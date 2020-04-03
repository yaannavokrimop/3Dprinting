import router from "../router/router";

export default {
    state: {
        authenticated: false,
        accessToken: null,
        userData: []
    },
    getters: {
        authenticated: state => state.authenticated,
        userData: state => state.userData
    },
    mutations: {
        loginStart: state => {
            state.authenticated = true
        },
        updateAccessToken: (state, accessToken) => {
            state.accessToken = accessToken;
            state.authenticated = (accessToken != null);
        },
        logout: (state) => {
            state.accessToken = null;
            state.authenticated = false;
        },
        setUserData: (state, payload) => {
            state.userData = payload;
        }
    },
    actions: {
        fetchAccessToken({commit}) {
            commit('updateAccessToken', localStorage.getItem('accessToken'));
        },
        doLogout({commit}) {
            localStorage.removeItem('accessToken');
            localStorage.removeItem('email');
            commit('logout');
            router.push('/signin');
        }
    }
}


