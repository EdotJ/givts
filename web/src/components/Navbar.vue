<template>
  <nav class="default-layout-navbar accent-main">
    <div class="left-navbar">
      <router-link to="/" class="icon">
        <IconBase>
          <GiftIcon />
        </IconBase>
      </router-link>
      <div class="navbar-links">
        <router-link to="/" class="navbar-url foreground-accent">
          Home
        </router-link>
        <router-link to="/giftees" class="navbar-url foreground-accent" v-if="loggedIn">
          Giftees
        </router-link>
        <router-link
          to="/admin"
          class="navbar-url foreground-accent"
          v-if="loggedIn && isAdmin"
        >
          Admin
        </router-link>
      </div>
    </div>
    <div class="right-navbar">
        <router-link to="/user" class="user-button">
        <IconBase icon-name="cogs" v-if="loggedIn">
          <Cog />
        </IconBase>
      </router-link>
      <router-link to="/logout" class="logout-button">
        <IconBase icon-name="logout" v-if="loggedIn">
          <LeaveIcon />
        </IconBase>
      </router-link>
      <div class="hamburger" v-on:click="toggleBurger">
        <Hamburger />
      </div>
      <transition name="slide">
        <div class="hamburger-menu" v-if="show">
          <router-link
            to="/"
            class="hamburger-menu-url foreground-accent"
            v-on:click.native="toggleBurger()"
          >
            Home
          </router-link>
          <router-link
            to="/giftees"
            class="hamburger-menu-url foreground-accent"
            v-on:click.native="toggleBurger()"
          >
            Giftees
          </router-link>
          <router-link
            to="/admin"
            class="hamburger-menu-url foreground-accent"
            v-on:click.native="toggleBurger()"
          >
            Admin page
          </router-link>
          <router-link
            to="/user"
            class="hamburger-menu-url foreground-accent"
            v-on:click.native="toggleBurger()"
          >
            Settings
          </router-link>
          <router-link
            to="/logout"
            class="hamburger-menu-url foreground-accent"
            v-if="loggedIn"
          >
            Log out
          </router-link>
        </div>
      </transition>
    </div>
  </nav>
</template>
<script>
import Hamburger from "./Hamburger";
import GiftIcon from "./icons/GiftIcon";
import IconBase from "./IconBase";
import { mapGetters } from "vuex";
import LeaveIcon from "./icons/LeaveIcon";
import Cog from "./icons/Cog";
import { GET_LOGGED_IN, IS_ADMIN } from "../store/getters.types";
import { USER_MODULE } from "../store/modules";

export default {
  name: "Navbar",
  components: { LeaveIcon, IconBase, GiftIcon, Hamburger, Cog },
  data() {
    return {
      show: false,
    };
  },
  methods: {
    toggleBurger() {
      this.show = !this.show;
    },
  },
  computed: mapGetters(USER_MODULE, {
    loggedIn: GET_LOGGED_IN,
    isAdmin: IS_ADMIN,
  }),
};
</script>

<style scoped>
.default-layout-navbar {
  height: 60px;
  display: flex;
  overflow: hidden;
  justify-content: space-between;
  padding: 0 0 0 10px;
  min-height: 60px;
}

.left-navbar {
  height: 100%;
  display: flex;
}

.right-navbar {
  display: flex;
  justify-content: center;
  align-items: center;
}

.icon {
  height: 100%;
  width: 60px;
  display: flex;
  justify-content: center;
  align-items: center;
  padding-right: 10px;
}

.navbar-links {
  height: 100%;
  display: flex;
}

.navbar-url {
  display: none;
  align-items: center;
  height: 100%;
  text-decoration: none;
  padding: 0 1rem;
}

.hamburger {
  width: 60px;
  height: 100%;
}

.hamburger:hover,
.navbar-url:hover,
.hamburger-menu-url:hover {
  background-color: #a93737;
}

.hamburger-menu {
  border-top: 3px solid black;
  opacity: 98%;
  background-color: #cd6565;
  position: absolute;
  left: 0;
  top: 60px;
  height: 300px;
  width: 100vw;
  padding: 10px 0;
  display: flex;
  flex-direction: column;
}

.hamburger-menu-url {
  width: 100%;
  padding: 10px 0;
  margin-top: 3px;
  background-color: #c44748;
  text-decoration: none;
}

.slide-enter-active {
  -moz-transition-duration: 0.2s;
  -webkit-transition-duration: 0.2s;
  -o-transition-duration: 0.2s;
  transition-duration: 0.2s;
}

.slide-leave-active {
  -moz-transition-duration: 0.2s;
  -webkit-transition-duration: 0.2s;
  -o-transition-duration: 0.2s;
  transition-duration: 0.2s;
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

.logout-button {
  display: none;
}

.user-button {
  display: none;
}

/* Mobile Styles */
@media only screen and (min-width: 415px) and (max-width: 960px) {
}

/* Tablet Styles */
@media only screen and (min-width: 480px) and (max-width: 960px) {
  .default-layout-navbar {
    justify-content: space-between;
    min-height: 60px;
  }

  .hamburger {
    display: none;
  }

  .navbar-url {
    display: flex;
  }

  .right-navbar {
    margin-right: 1rem;
  }

  .logout-button, .user-button {
    display: flex;
    color: #201b22;
    padding-left: 0.75rem;
    padding-right: 0.25rem;
    height: 100%;
  }

  .logout-button {
    padding-top: 16px;
  }

  .logout-button:hover, .user-button:hover {
    background: #a93737;
  }

  .user-button {
    padding-top: 20px;
  }
}

/* Desktop Styles */
@media only screen and (min-width: 961px) {
  .default-layout-navbar {
    justify-content: space-between;
    min-height: 60px;
  }

  .hamburger {
    display: none;
  }

  .navbar-url {
    display: flex;
  }

  .right-navbar {
    margin-right: 1rem;
  }

  .logout-button, .user-button {
    display: flex;
    color: #201b22;
    padding-left: 0.75rem;
    padding-right: 0.25rem;
    height: 100%;
  }

  .logout-button {
    padding-top: 16px;
  }

  .logout-button:hover, .user-button:hover {
    background: #a93737;
  }

  .user-button {
    padding-top: 20px;
  }
}
</style>
