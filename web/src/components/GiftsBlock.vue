<template>
  <div class="gifts-container">
    <div class="gifts" v-if="!isLoadingGifts">
      <Gift
        v-for="gift in gifts"
        :key="gift.id"
        :gift="gift"
        :gifteeId="gifteeId"
        :occasionId="`${occasionId}`"
      />
      <div class="hint" v-if="occasionId === -1">
        Select an occasion to see assigned gifts!
      </div>
      <div class="hint" v-else-if="gifts.length === 0">
        Add some gifts and they'll show up here!
      </div>
    </div>
    <Loader v-else-if="isLoadingGifts" />
  </div>
</template>

<script>
import { mapState, mapGetters } from "vuex";
import { GIFTS_MODULE, OCCASIONS_MODULE } from "../store/modules";
import { GET_BY_OCCASION } from "../store/getters.types";
import Gift from "./Gift";
import Loader from "./Loader";

export default {
  name: "GiftsBlock",
  components: {
    Gift,
    Loader,
  },
  props: {
    gifteeId: {
      type: String,
      required: true,
    },
  },
  computed: {
    ...mapState(OCCASIONS_MODULE, {
      occasionId: (state) => state.selectedOccasion,
    }),
    ...mapState(GIFTS_MODULE, {
      isLoadingGifts: (state) => state.isLoading,
    }),
    ...mapGetters(GIFTS_MODULE, {
      getGiftsByOccasion: GET_BY_OCCASION,
    }),
    gifts: function() {
      return this.occasionId === -1
        ? []
        : this.getGiftsByOccasion(this.occasionId);
    },
  },
};
</script>

<style scoped>
.gifts-container {
  display: flex;
  justify-content: space-between;
  width: 100%;
  padding: 1rem;
}

.gifts {
  display: flex;
  flex-direction: row;
  flex-wrap: wrap;
  justify-content: center;
  width: 95%;
}

.gift {
  flex-shrink: 1;
  max-width: 47%;
  border: 1px solid;
  margin: 0.5rem;
  min-height: 200px;
}

.hint {
  min-height: 1rem;
  display: block;
}

@media only screen and (min-width: 1280px) and (max-width: 1560px) {
  .gift {
    flex-shrink: 1;
    max-width: 30%;
    border: 1px solid;
    margin: 0.5rem;
  }
}

@media only screen and (min-width: 1560px) and (max-width: 19200px) {
}
</style>
