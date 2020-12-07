<template>
  <div v-if="giftee">
    <div>
      <div class="giftee-container">
        <div class="occasions-block-container">
          <OccasionsBlock
            class="occasions-block-desktop"
            :gifteeId="this.$route.params.id"
          />
        </div>
        <div class="giftee-block-container">
          <GifteeBlock :giftee="giftee" />
          <div class="context-buttons">
            <Button
              iconName="exclamation"
              class="delete-button"
              @click.native="deleteGiftee()"
            >
              Delete
            </Button>
            <router-link :to="`/giftees/edit/${giftee.id}`">
              <Button class="edit-button">
                Edit
              </Button>
            </router-link>
          </div>
        </div>
      </div>
      <OccasionsBlockMobile
        class="occasions-block-mobile"
        :gifteeId="this.$route.params.id"
      />
    </div>
    <GiftsBlock
      class="gifts-block"
      :gifteeId="this.$route.params.id"
    ></GiftsBlock>
  </div>
</template>

<script>
import { mapActions, mapGetters } from "vuex";
import Button from "../components/Button.vue";
import GifteeBlock from "../components/GifteeBlock.vue";
import GiftsBlock from "../components/GiftsBlock.vue";
import OccasionsBlock from "../components/OccasionsBlock.vue";
import OccasionsBlockMobile from "../components/OccasionsBlockMobile.vue";
import { DELETE, GET_ALL } from "../store/actions.types";
import { GET_BY_ID } from "../store/getters.types";
import { GIFTEES_MODULE, OCCASIONS_MODULE } from "../store/modules";
export default {
  name: "Giftee",
  components: {
    GifteeBlock,
    OccasionsBlock,
    OccasionsBlockMobile,
    Button,
    GiftsBlock,
  },
  computed: {
    ...mapGetters(GIFTEES_MODULE, {
      getGiftee: [GET_BY_ID],
    }),
    giftee: function() {
      return this.getGiftee(parseInt(this.$route.params.id));
    },
  },
  methods: {
    ...mapActions(OCCASIONS_MODULE, {
      getOccasions: GET_ALL,
    }),
    ...mapActions(GIFTEES_MODULE, {
      delete: DELETE,
    }),
    deleteGiftee() {
      this.delete(parseInt(this.$route.params.id)).then(() => {
        this.$router.push("/giftees");
      });
    },
  },
  mounted() {
    this.getOccasions({ gifteeId: parseInt(this.$route.params.id) });
  },
};
</script>

<style scoped>
.content {
  display: flex;
  flex-direction: column;
}

.giftee-container {
  padding: 1rem;
}

.context-buttons {
  padding-top: 0.5rem;
  display: flex;
  justify-content: space-between;
}

.occasions-block-desktop {
  display: none;
}

.giftee-block {
  width: 100%;
}

.gifts-block {
  display: none;
}

/* Tablet Styles */
@media only screen and (min-width: 650px) and (max-width: 960px) {
  .occasions-block-mobile {
    display: none;
  }

  .occasions-block-desktop {
    display: inline-block;
    height: 100px;
  }

  .giftee-container {
    width: 100%;
    display: flex;
  }

  .giftee-block-container {
    width: calc(50% - 2rem);
    margin-left: 0.5rem;
  }

  .occasions-block-container {
    width: calc(50% - 1.5rem);
    margin-right: 0.5rem;
    display: block;
  }

  .gifts-block {
    margin-top: 1rem;
    display: block;
  }
}

/* Desktop Styles */
@media only screen and (min-width: 961px) {
  .occasions-block-mobile {
    display: none;
  }

  .occasions-block-desktop {
    display: inline-block;
    height: 100px;
  }

  .giftee-container {
    width: 100%;
    display: flex;
  }

  .giftee-block-container {
    width: calc(50% - 2rem);
    margin-left: 0.5rem;
  }

  .occasions-block-container {
    width: calc(50% - 1.5rem);
    margin-right: 0.5rem;
    display: block;
  }

  .gifts-block {
    margin-top: 1rem;
    display: block;
  }
}
</style>
