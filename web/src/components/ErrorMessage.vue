<template>
  <div class="error">
    <IconBase class="cross-icon" width="16" height="16" view-box="0 0 16 16">
      <Cross />
    </IconBase>
    <p class="error-text">
      <slot></slot>
    </p>
  </div>
</template>
<script>
import IconBase from "./IconBase";
import Cross from "./icons/Cross";

export default {
  name: "ErrorMessage",
  components: { Cross, IconBase },
  props: {
    text: { type: String, default: "Something went wrong!" },
  },
};
</script>

<style scoped>
.error {
  display: flex;
  align-items: center;
}

.error-text {
  font-size: 0.75rem;
  color: #201b22;
}

.cross-icon {
  color: red;
  padding: 0 0.5rem;
}
</style>
