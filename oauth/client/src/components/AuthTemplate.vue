<template>
  <div class="grid grid-cols-6 h-screen">
    <div class="flex flex-col col-span-6 lg:col-span-2">
      <div class="flex-1 flex flex-content-start text-xl mt-3 ml-3 text-xl">
        <router-link
          to="/"
          class="text-white"
        >
          <font-awesome-icon icon="arrow-left" />
          Home
        </router-link>
      </div>
      <slot />
    </div>
    <div class="bg-auth-bg bg-cover invisible lg:visible lg:col-span-4" />
  </div>
</template>

<script>

export default {
  name: 'AuthTemplate',
}
</script>