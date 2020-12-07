<template>
  <div class="image-container">
    <Loader v-if="isLoading">
    </Loader>
    <img v-else :src="image" />
  </div>
</template>

<script>
import imageService from "@/services/imageService";
import Loader from "./Loader.vue";

export default {
  name: "HostedImage",
  components: {
    Loader,
  },
  props: {
    imageId: {
      type: String,
      required: true,
    },
  },
  data() {
    return {
      isLoading: false,
      image: undefined
    };
  },
  watch: {
    imageId: {
      immediate: true,
      handler() {
        if (this.imageId !== this.previousImageId) {
          if (!this.image) {
            this.isLoading = true;
          }
          imageService
            .getImage(this.imageId)
            .then((res) => {
              this.image = window.URL.createObjectURL(res);
            })
            .finally(() => {
              this.isLoading = false;
            });
          this.previousImageId = this.imageId;
        }
      },
    },
  },
};
</script>

<style scoped>
.image-container {
  max-width: 80%;
  padding-top: 0.25rem;
  flex-shrink: 1;
  min-width: 30px;
  max-height: 300px;
  padding: 0.5rem 0;
}

img {
  max-width: 100%;
  max-height: 100%;
}
</style>
