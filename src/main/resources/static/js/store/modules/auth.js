import router from "router/router";

export default {
    state: {
        authenticated: false,
        accessToken: null,
        authority: null,
        userData: []
    },
    getters: {
        authenticated: state => state.authenticated,
        userData: state => state.userData,
        authority: state => state.authority
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
            state.authority = null;
        },
        setUserAuthority: (state, authority) => {
            state.authority = authority;
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
            localStorage.clear();
            commit('logout');
            router.push('/signin');
        }
    }
}


