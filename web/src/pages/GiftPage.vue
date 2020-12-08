<template>
  <div class="container">
    <h1>{{ this.$route.params.giftId ? "Edit" : "Add" }} a gift</h1>
    <div
      class="form"
      v-if="!isLoading"
      v-on:keyup.enter="$route.params.giftId ? updateGift() : createGift()"
    >
      <div class="form-items">
        <div class="error" v-if="errors.name">
          {{ errors.name }}
        </div>
        <div class="input-group" :class="{ spacing: !errors.name }">
          <label for="name">Name</label>
          <input
            type="text"
            v-model="gift.name"
            id="name"
            placeholder="Very cool gift"
          />
        </div>
        <div class="error" v-if="errors.price">
          {{ errors.price }}
        </div>
        <div class="input-group" :class="{ spacing: !errors.price }">
          <label for="price">Price</label>
          <input
            type="number"
            v-model="gift.price"
            id="price"
            placeholder="4.99"
            step="any"
          />
        </div>
        <div class="error" v-if="errors.description">
          {{ errors.description }}
        </div>
        <div class="input-group" :class="{ spacing: !errors.description }">
          <label for="description">Description</label>
          <textarea
            type="text"
            v-model="gift.description"
            id="description"
            placeholder="This is text describing this gift perfectly!"
          />
        </div>
        <div class="error" v-if="errors.picture">
          {{ errors.picture }}
        </div>
        <div class="input-group">
          <label for="picture">Picture</label>
          <input
            type="file"
            ref="file"
            id="picture"
            accept="image/*"
            @change="handleFileUpload()"
          />
        </div>
      </div>
      <div class="context-buttons">
        <Button
          class="submit-button"
          v-on:click.native="$route.params.giftId ? updateGift() : createGift()"
        >
          Submit
        </Button>
      </div>
    </div>
  </div>
</template>

<script>
import { GIFTS_MODULE } from "../store/modules";
import { CREATE, UPDATE, DELETE } from "../store/actions.types";
import giftsService from "../services/giftsService";
import { mapActions, mapState } from "vuex";
import Button from "../components/Button";
import { SET_IS_LOADING } from "../store/mutations.types";

export default {
  name: "Gift",
  components: {
    Button,
  },
  data() {
    return {
      giftId: parseInt(this.$route.params.giftId),
      gift: {},
      errors: {
        name: "",
        price: "",
        description: "",
        picture: "",
      },
      file: "",
    };
  },
  computed: {
    ...mapState({
      isLoading: (state) => state.isLoading,
    }),
  },
  methods: {
    ...mapActions(GIFTS_MODULE, {
      create: CREATE,
      update: UPDATE,
      delete: DELETE,
    }),
    createGift() {
      if (this.handleErrors()) {
        return;
      }
      this.create({
        gifteeId: this.$route.params.gifteeId,
        occasionId: this.$route.params.occasionId,
        gift: this.gift,
        file: this.file,
      }).catch((err) => {
        if (err.response && err.response.status === 413) {
          this.errors.picture = err.response.data.permittedSize
            ? `Image is too large. Max size is ${err.response.data.permittedSize} MB`
            : "Image is too large";
          this.hasError = true;
        }
      });
    },
    updateGift() {
      if (this.handleErrors()) {
        return;
      }
      this.update({
        gifteeId: this.$route.params.gifteeId,
        occasionId: this.$route.params.occasionId,
        gift: this.gift,
        file: this.file,
      })
        .then(() => {})
        .catch((err) => {
          if (err.response && err.response.status === 413) {
            this.errors.picture = err.response.data.permittedSize
              ? `Image is too large. Max size is ${err.response.data.permittedSize} MB`
              : "Image is too large";
            this.hasError = true;
          }
        });
    },
    deleteGift() {
      this.delete({
        gifteeId: this.$route.params.gifteeId,
        giftId: this.giftId,
      }).then(() => {
        this.$router.push(`/giftees/${this.$route.params.gifteeId}`);
      });
    },
    getGift() {
      this.$store.commit(SET_IS_LOADING, true, { root: true });
      return giftsService
        .getSingle(
          this.$route.params.gifteeId,
          this.$route.params.occasionId,
          this.$route.params.giftId
        )
        .then((res) => {
          return res;
        });
    },
    handleErrors() {
      let hasError = false;
      if (!this.gift.name) {
        this.errors.name = "Name cannot be empty!";
        hasError = true;
      } else {
        this.errors.name = "";
      }
      if (!this.gift.price || this.gift.price < 0) {
        this.errors.price = !this.gift.price
          ? "Price cannot be empty!"
          : "Price cannot be negative!";
        hasError = true;
      } else {
        this.errors.price = "";
      }
      const isDescriptionError =
        !this.$route.params.giftId &&
        !this.file &&
        this.gift.description &&
        this.gift.description.length < 50;
      if (
        (!this.file && !this.gift.description && !this.gift.image_id) ||
        isDescriptionError
      ) {
        this.errors.description = isDescriptionError
          ? "A picture is worth a thousand words. Give a better description. (at least 50 characters)"
          : "You should either add an image or add a description";
        hasError = true;
      } else {
        this.errors.description = "";
      }
    
      return hasError;
    },
    handleFileUpload() {
      this.file = this.$refs.file.files[0];
    },
  },
  mounted() {
    if (this.$route.params.giftId) {
      this.getGift()
        .then((res) => {
          this.gift = res;
        })
        .finally(() => {
          this.$store.commit(SET_IS_LOADING, false, { root: true });
        });
    } else {
      this.gift = {
        name: "",
        date: "",
      };
    }
  },
};
</script>

<style scoped>
h1 {
  margin: 0;
  padding: 2rem 0;
}

.container {
  display: flex;
  flex-direction: column;
  align-items: center;
}

.form {
  display: flex;
  width: 80%;
  flex-direction: column;
  align-items: center;
  justify-content: center;
}

.form-items {
  display: flex;
  flex-direction: column;
  align-items: flex-start;
  width: 100%;
}

.error {
  font-size: 1rem;
  color: red;
  padding: 0;
  border-radius: 3px;
  min-height: 1.25rem;
}

.spacing {
  margin-top: 1.25rem;
}

.input-group {
  margin-bottom: 0.5rem;
  min-height: 3rem;
  width: 100%;
  display: flex;
  justify-content: space-between;
  align-items: center;
}

label {
  display: inline-block;
  min-width: 90px;
  text-align: left;
  font-size: 1rem;
}

input,
textarea {
  min-height: 2rem;
  flex-basis: auto;
  flex-grow: 1;
  min-width: 0;
  background: #fbfaf8;
  border: 1px solid #201b22;
  border-radius: 3px;
  padding-left: 0.5rem;
}

#picture {
  border: none;
  background: transparent;
  padding-left: 0;
}

textarea {
  min-height: 5rem;
  font-size: 1rem;
  font-family: Avenir, Helvetica, Arial, sans-serif;
  padding: 0.25rem;
}

.context-buttons {
  display: flex;
  justify-content: flex-end;
  align-items: center;
  width: 100%;
}

.delete-button {
  color: red;
  text-decoration: underline;
  font-size: 1rem;
  cursor: pointer;
}

input::-webkit-outer-spin-button,
input::-webkit-inner-spin-button {
  -webkit-appearance: none;
  margin: 0;
}

input[type="number"] {
  -moz-appearance: textfield;
}

/* Tablet Styles */
@media only screen and (min-width: 415px) and (max-width: 960px) {
  .form {
    width: 60%;
  }
}

/* Desktop Styles */
@media only screen and (min-width: 961px) {
  .form {
    width: 40%;
  }
}
</style>
