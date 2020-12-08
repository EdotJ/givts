import App from "./App";
import Home from "./components/Home";
import Login from "./components/Login";
import Register from "./components/Register";
import ClientPage from "./components/ClientPage";
import ClientDetailsPage from "./components/ClientDetailsPage";
import AddClientPage from "./components/AddClientPage";
import AuthorizeClient from "./components/AuthorizeClient";
import VueRouter from "vue-router";

const router = new VueRouter({
    mode: "history",
    routes: [
        {
            path: '/',
            component: App,
            children: [
                {
                    path: '',
                    component: Home
                },
            ]
        },
        {
            path: '/login',
            component: Login,
            meta: {
                title: 'Login'
            }
        },
        {
            path: '/register',
            component: Register,
            meta: {
                title: 'Register'
            }
        },
        {
            path: '/client',
            component: ClientPage,
        },
        {
            path: '/client/add',
            name: 'Add Client',
            component: AddClientPage,
        },
        {
            path: '/client/:id',
            name: 'Client',
            component: ClientDetailsPage,
            props: true
        },
        {
            path: '/oauth/authorize',
            name: 'Authorize Application',
            component: AuthorizeClient,
        }
    ]
});

export default router;