import Home from "./pages/Home";
import UserPage from "./pages/UserPage";
import Giftees from "./pages/Giftees";
import SingleGiftee from "./pages/SingleGiftee";
import OccasionPage from "./pages/OccasionPage";
import GiftPage from "./pages/GiftPage";
import AddGiftee from "./pages/AddGiftee";
import AdminPage from "./pages/AdminPage";
import VueRouter from "vue-router";
import store from "./store/index";
import { GET_ACCESS_TOKEN, LOGOUT } from "./store/actions.types";
import { USER_MODULE } from "./store/modules";


const router = new VueRouter({
  mode: "history",
  routes: [
    {
      path: "/",
      component: Home,
    },
    {
      path: "/user",
      component: UserPage,
    },
    {
      path: "/giftees",
      component: Giftees,
    },
    {
      path: "/authorizer/callback",
      component: () => {
        store.dispatch(USER_MODULE + "/" + GET_ACCESS_TOKEN);
      },
    },
    {
      path: "/logout",
      component: () => {
        store.dispatch("logout").then(() => {
          window.location = "/";
        });
      },
    },
    {
      path: "/giftees/add",
      component: AddGiftee,
    },
    {
      path: "/giftees/edit/:id",
      component: AddGiftee,
    },
    {
      path: "/giftees/:id",
      component: SingleGiftee,
    },
    {
      path: "/giftees/:gifteeId/occasions/:occasionId?",
      component: OccasionPage,
    },
    {
      path: "/giftees/:gifteeId/occasions/:occasionId/gifts/:giftId?",
      component: GiftPage,
    },
    {
      path: "/admin",
      component: AdminPage
    },
  ],
});

router.beforeEach((to, from, next) => {
  const publicPages = ["/", "/authorizer/callback"];
  const authRequired = !publicPages.includes(to.path);
  const user = localStorage.getItem("user");
  const loggedIn = user && JSON.parse(user);
  if (authRequired && !loggedIn) {
    store.dispatch(LOGOUT);
    return next("/");
  }
  const loggedInWithoutAccessToken = loggedIn && !loggedIn.accessToken;
  const authRequiredAndUnauthorized = authRequired && !loggedIn.accessToken;
  if (loggedInWithoutAccessToken || authRequiredAndUnauthorized) {
    store.dispatch(LOGOUT);
    return next("/");
  }
  next();
});

export default router;
