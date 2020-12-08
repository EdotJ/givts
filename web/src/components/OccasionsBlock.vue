<template>
  <div class="occasions-block">
    <div class="button-container">
      <div class="table-title">
        Occasions
      </div>
      <router-link :to="`/giftees/${gifteeId}/occasions`" class="link">
        <Button name="Add new" class="add-button">
          Add new
        </Button>
      </router-link>
    </div>
    <div v-if="!isLoading">
      <div
        class="occasion-gift-container"
        v-for="(occasion, index) in occasions"
        :key="occasion.id"
        :class="{
          'occasion-gift-container-last': index === occasions.length - 1,
          highlight: occasion.id === selected,
        }"
      >
        <div class="occasions-block" @click="toggleSelection(occasion.id)">
          <div class="occasion">
            <div class="occasion-data">
              <div class="occasion-name">
                {{ occasion.name }}
              </div>
              <div class="occasion-date">{{ occasion.date }}</div>
            </div>
            <div class="occasion-context">
              <router-link
                :to="`/giftees/${gifteeId}/occasions/${occasion.id}/gifts`"
                class="link"
                v-on:click.native.stop
              >
                <Button name="Add new" class="add-gift-button">
                  +
                </Button>
              </router-link>
              <router-link
                :to="`/giftees/${gifteeId}/occasions/${occasion.id}`"
                class="link"
                v-on:click.native.stop
              >
                <Button name="Edit occasion" class="edit-occasion-button">
                  <IconBase
                    icon-name="edit-icon"
                    viewBox="0 0 16 16"
                    height="16"
                    width="16"
                    class="icon-base"
                  >
                    <EditIcon />
                  </IconBase>
                </Button>
              </router-link>
            </div>
          </div>
        </div>
      </div>
    </div>
    <Loader v-else/>
  </div>
</template>

<script>
import { mapState, mapActions, mapMutations } from "vuex";
import Button from "./Button";
import IconBase from "./IconBase";
import EditIcon from "./icons/EditIcon";
import Loader from "./Loader";
import { OCCASIONS_MODULE, GIFTS_MODULE } from "@/store/modules";
import { GET_ALL } from "@/store/actions.types";
import { SET_SELECTED_OCCASION } from "../store/mutations.types";

export default {
  name: "OccasionsBlock",
  components: {
    Button,
    IconBase,
    EditIcon,
    Loader
  },
  props: {
    gifteeId: {
      type: String,
      required: true,
    },
  },
  data() {
    return {
      toggledOccasion: -1,
    };
  },
  computed: {
    ...mapState(OCCASIONS_MODULE, {
      occasions: (state) => state.occasions,
      isLoading: (state) => state.isLoading,
      selected: (state) => state.selectedOccasion,
    }),
  },
  methods: {
    ...mapActions(GIFTS_MODULE, {
      getGifts: GET_ALL,
    }),
    ...mapMutations(OCCASIONS_MODULE, {
      setSelected: SET_SELECTED_OCCASION,
    }),
    toggleSelection(id) {
      if (this.selected === id) {
        this.setSelected(-1);
      } else {
        this.setSelected(id);
        this.getGifts({ gifteeId: this.gifteeId, occasionId: id });
      }
    },
  },
};
</script>

<style scoped>
.occasions-block {
  width: 100%;
}

.button-container {
  width: 100%;
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.table-title {
  font-size: 1.5rem;
}

.add-button {
  margin: 0 0 0.5rem 1rem;
  padding: 0.25rem;
  font-size: 1.25rem;
}

.occasion-gift-container {
  border: 2px solid #585858;
  border-bottom: none;
}

.occasion-gift-container:last-child {
  border: 2px solid #585858;
}

.occasions-block {
  min-height: 60px;
  display: flex;
  align-items: center;
}

.bottom-border {
  border-bottom: 1px solid #585858;
  margin-bottom: 2px;
}

.occasion {
  display: flex;
  justify-content: space-between;
  width: 100%;
  align-items: center;
  font-size: 1rem;
}

.occasion-data {
  display: flex;
  flex-direction: row;
  justify-content: space-evenly;
  align-items: center;
  min-width: 60%;
}

.occasion-context {
  display: flex;
  flex-direction: row;
  min-width: 40%;
  align-items: center;
  justify-content: flex-end;
}

.add-gift-button,
.edit-occasion-button {
  min-height: 45px;
  min-width: 45px;
  border-radius: 3rem;
}

.edit-occasion-button {
  margin-left: 0.5rem;
  margin-right: 0.5rem;
}

.highlight {
  background: #c44748;
  transition: 0.3s;
}

.highlight .add-gift-button,
.highlight .edit-occasion-button {
  background: #a93737;
  border: 2px solid #a93737;
  color: #fbfaf8;
  transition: 0.3s;
}

.highlight .add-gift-button:hover,
.highlight .edit-occasion-button:hover {
  background: #fbfaf8;
  border: 2px solid #fbfaf8;
  color: #a93737;
  transition: 0.3s;
}
</style>
