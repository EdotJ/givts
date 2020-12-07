<template>
  <div class="giftee-block">
    <div class="name">
      {{ giftee.name }}
    </div>
    <div class="description-block">
      <div class="desc-item">
        <div class="desc-title">
          Likes:
        </div>
        <div class="item-content">
          {{giftee.likes}}
        </div>
      </div>
      <div class="desc-item">
        <div class="desc-title">
          Dislikes:
        </div>
        <div class="item-content">
          {{giftee.dislikes}}
        </div>
      </div>
      <div class="desc-item desc-item-last">
        <div class="desc-title">
          Hobbies:
        </div>
        <div class="item-content">
          {{giftee.hobbies}}
        </div>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: "GifteeBlock",
  props: {
    giftee: {type: Object}
  }
};
</script>

<style scoped>
.giftee-block {
  border: 2px solid #585858;
  border-radius: 3px;
}

.name {
  width: 100%;
  font-size: 1.5rem;
  font-weight: 600;
  background: #c44748;
  color: #fbfaf8;
  padding: 0.25rem 0;
}

.description-block {
  display: flex;
  flex-direction: column;
  justify-items: left;
  align-content: left;
  text-align: left;
}

.desc-item {
  padding: 0.25rem 0 0 0.5rem;
  display: flex;
  justify-content: space-between;
  align-items: center;
  border-bottom: 1px solid #c44748;
}

.desc-item-last {
  border-bottom: 0px;
}

.item-content {
  display: flex;
  flex-direction: column;
  text-align: right;
  padding-right: 0.5rem;
  font-size: 1rem;
}

/* Tablet Styles */
@media only screen and (min-width: 415px) and (max-width: 960px) {
}

/* Desktop Styles */
@media only screen and (min-width: 961px) {
}
</style>
