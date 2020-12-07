<template>
  <div class="container">
    <IconBase icon-name="hamburger">
      <HamburgerIcon />
    </IconBase>
  </div>
</template>

<script>
import IconBase from "./IconBase";
import HamburgerIcon from "./icons/HamburgerIcon";

export default {
  name: "Hamburger",
  components: { HamburgerIcon, IconBase },
};
</script>

<style scoped>
.container {
  display: flex;
  justify-content: center;
  height: 100%;
  align-items: center;
}
svg {
}
</style>
