<template>
  <div class="occasions-container">
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
    <div
      class="occasion-gift-container"
      v-for="(occasion, index) in occasions"
      :key="occasion.id"
      :class="{
        'occasion-gift-container-last': index === occasions.length - 1,
      }"
    >
      <div
        class="occasions-block"
        @click="occasion.giftCount > 0 && toggleSlide(occasion.id)"
      >
        <div class="occasion">
          <div class="occasion-data">
            <div class="occasion-name">
              {{ occasion.name }}
            </div>
            <div class="occasion-date">{{ occasion.date }}</div>
          </div>
          <div class="occasion-context">
            <IconBase
              viewport="0 0 16 16"
              height="16"
              width="32"
              class="slide-icon down"
              icon-name="down"
              v-if="occasion.id !== toggledOccasion && occasion.giftCount > 0"
            >
              <SlideDown />
            </IconBase>
            <IconBase
              viewport="0 0 16 16"
              height="16"
              width="32"
              class="slide-icon up"
              icon-name="up"
              v-if="occasion.id === toggledOccasion && occasion.giftCount > 0"
            >
              <SlideUp />
            </IconBase>
            <router-link
              :to="`/giftees/${gifteeId}/occasions/${occasion.id}/gifts`"
              class="link"
            >
              <Button name="Add new" class="add-gift-button">
                +
              </Button>
            </router-link>
            <router-link
              :to="`/giftees/${gifteeId}/occasions/${occasion.id}`"
              class="link"
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
      <transition name="slide">
        <div class="gifts-container" v-if="occasion.id === toggledOccasion">
          <div class="gifts" v-if="!isLoadingGifts">
            <Gift
              v-for="gift in gifts"
              :key="gift.id"
              :gift="gift"
              :gifteeId="gifteeId"
              :occasionId="`${occasion.id}`"
            />
          </div>
          <Loader v-else> </Loader>
        </div>
      </transition>
    </div>
  </div>
</template>

<script>
import Button from "./Button";
import Gift from "./Gift";
import IconBase from "./IconBase";
import SlideUp from "./icons/SlideUp";
import SlideDown from "./icons/SlideDown";
import EditIcon from "./icons/EditIcon";
import Loader from "./Loader";
import { mapState, mapActions, mapGetters } from "vuex";
import { GIFTS_MODULE, OCCASIONS_MODULE } from "../store/modules";
import { GET_ALL } from "../store/actions.types";
import { GET_BY_OCCASION } from "../store/getters.types";

export default {
  name: "OccasionsBlockMobile",
  components: {
    Button,
    Gift,
    IconBase,
    SlideUp,
    SlideDown,
    EditIcon,
    Loader,
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
    }),
    ...mapState(GIFTS_MODULE, {
      isLoadingGifts: (state) => state.isLoading,
    }),
    ...mapGetters(GIFTS_MODULE, {
      getGiftsByOccasion: GET_BY_OCCASION,
    }),
    gifts: function() {
      return this.toggledOccasion === -1
        ? []
        : this.getGiftsByOccasion(this.toggledOccasion);
    },
  },
  methods: {
    ...mapActions(GIFTS_MODULE, {
      getGifts: GET_ALL,
    }),
    toggleSlide(id) {
      if (this.toggledOccasion === id) {
        this.toggledOccasion = -1;
      } else {
        this.toggledOccasion = id;
        this.getGifts({ gifteeId: this.gifteeId, occasionId: id });
      }
    },
  },
};
</script>

<style scoped>
.button-container {
  width: 100%;
  display: flex;
  justify-content: space-between;
  align-items: flex-end;
}

.table-title {
  font-size: 2rem;
}

.add-button {
  margin: 0.5rem 0 0.5rem 1rem;
  padding: 1rem;
}

.occasions-container {
  padding: 0.5rem 1rem;
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

.gifts-container {
  display: flex;
  flex-direction: column;
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

.gifts {
  display: flex;
  flex-direction: row;
  flex-wrap: wrap;
}

.link {
  text-decoration: none;
  color: inherit;
}

.slide-enter-active {
  -moz-transition-duration: 0.1s;
  -webkit-transition-duration: 0.1s;
  -o-transition-duration: 0.1s;
  transition-duration: 0.1s;
}

.slide-leave-active {
  -moz-transition-duration: 0.1s;
  -webkit-transition-duration: 0.1s;
  -o-transition-duration: 0.1s;
  transition-duration: 0.1s;
}

.slide-enter-to,
.slide-leave {
  max-height: 100px;
  overflow: hidden;
}

.slide-enter,
.slide-leave-to {
  overflow: hidden;
  max-height: 0;
}
</style>
