<template>
  <div class="gift">
    <div class="top">
      <Button class="context-button trash-button" @click.native="deleteGift()">
        <IconBase
          icon-name="delete-icon"
          viewBox="0 0 16 16"
          height="16"
          width="16"
          class="icon-base"
        >
          <TrashCan />
        </IconBase>
      </Button>
      <HostedImage
        class="middle-element"
        v-if="gift.image_id"
        :imageId="gift.image_id"
      />
      <div class="middle-element" v-else>
        {{ gift.description }}
      </div>

      <div class="right-buttons">
        <router-link
          :to="`/giftees/${gifteeId}/occasions/${occasionId}/gifts/${gift.id}`"
        >
          <Button class="context-button edit-button">
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
    <div class="gift-writeup">
      <div class="inline-params" :class="{ 'bottom-border': gift.description }">
        <span class="gift-name">
          {{ gift.name }}
        </span>
        <span class="gift-price"> {{ gift.price }} € </span>
      </div>
      <div class="gift-description" v-if="gift.description && gift.image_id">
        {{ gift.description }}
      </div>
    </div>
  </div>
</template>

<script>
import Button from "./Button";
import IconBase from "./IconBase";
import EditIcon from "./icons/EditIcon";
import TrashCan from "./icons/TrashCan";
import HostedImage from "./HostedImage";
import { mapActions } from "vuex";
import { GIFTS_MODULE } from "../store/modules";
import { DELETE } from "../store/actions.types";

export default {
  name: "Gift",
  props: {
    gifteeId: { type: String, required: true },
    occasionId: { type: String, required: true },
    gift: { type: Object },
  },
  components: {
    Button,
    IconBase,
    EditIcon,
    TrashCan,
    HostedImage,
  },
  methods: {
    ...mapActions(GIFTS_MODULE, {
      remove: DELETE,
    }),
    deleteGift() {
      this.remove({
        gifteeId: this.gifteeId,
        occasionId: this.occasionId,
        giftId: this.gift.id,
      });
    },
  },
};
</script>

<style scoped>
.gift {
  width: 100%;
  border-top: 1px solid #585858;
  display: flex;
  flex-direction: column;
}

.top {
  display: flex;
  width: 100%;
  justify-content: space-evenly;
}

.gift-writeup {
  display: flex;
  flex-direction: column;
  border-top: 1px solid #585858;
  flex-grow: 1;
}

.inline-params {
  display: flex;
  justify-content: space-between;
  padding: 0 1rem;
  font-size: 1.25rem;
  height: 100%;
  align-items: center;
}

.inline-params span {
  flex-grow: 1;
  flex-basis: 0;
}

.gift-name {
  height: 100%;
  display: flex;
  align-items: center;
  justify-content: center;
  color: #c44748;
}

.gift-price {
  border-left: 1px solid #585858;
  height: 100%;
  display: flex;
  align-items: center;
  justify-content: center;
}

.gift-description {
  font-size: 0.8rem;
  text-align: left;
  padding: 0.25rem;
}

.gift-button {
  margin: 0.25rem;
  border-radius: 50px;
  height: 3rem;
  width: 3rem;
}

.context-button {
  margin: 0.25rem;
  border-radius: 50px;
  background: transparent;
  color: tomato;
  transition: 0.3s;
  height: 3rem;
  width: 3rem;
}

.trash-button {
  flex-basis: auto;
  min-width: 3rem;
  flex-shrink: 0;
}

.right-buttons {
  flex-basis: auto;
  min-width: 3rem;
  flex-shrink: 0;
  display: flex;
  flex-direction: column;
}

.bottom-border {
  border-bottom: 1px solid #585858;
}

.middle-element {
  word-break: break-all;
  max-width: 80%;
  padding-top: 0.25rem;
  flex-shrink: 1;
  min-width: 30px;
  max-height: 250px;
  padding: 0.5rem 0;
}

/* Tablet Styles */
@media only screen and (min-width: 700px) and (max-width: 960px) {
  .gift {
    width: calc(50% - 2px);
    flex-grow: 1;
  }
  
  .gift:nth-of-type(odd) {
    border-right: 1px solid #585858;
  }
}
</style>
