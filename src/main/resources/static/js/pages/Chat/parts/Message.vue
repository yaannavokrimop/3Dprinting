<template>
    <div>
        <v-app-bar
                color="blue"
                dark
                dense
                absolute
        >
            <v-toolbar-title>
                <a :href="'#/user/'+companionId">
                    <div class="link">
                        <h3>{{currentChat.companion}}</h3>
                    </div>
                </a>

            </v-toolbar-title>
        </v-app-bar>
        <div class="mt-10"></div>
        <v-container class="messages">
            <v-content class="background">

                <ul>
                    <li
                            style="list-style: none;"
                            v-for="(message,index) in messages"
                            :key="index"
                    >
                        <div v-if="message.author.id === companionId">
                            <ul
                                    class="companion"
                            >
                                <v-list-item-content>
                                    <v-list-item-subtitle>
                                        <strong>{{message.author.name+" "+message.author.surname}}</strong>
                                    </v-list-item-subtitle>
                                    <v-list-item-title>
                                        <h5>
                                            <strong>{{message.text}}</strong>
                                        </h5>
                                    </v-list-item-title>
                                    <v-list-item-subtitle>
                                        <strong>{{message.date}}</strong>
                                    </v-list-item-subtitle>
                                </v-list-item-content>
                            </ul>
                        </div>
                        <div v-else>
                            <ul class="speech-bubble">
                                <v-list-item-content>
                                    <v-list-item-subtitle>
                                        <strong>{{message.author.name+" "+message.author.surname}}</strong>
                                    </v-list-item-subtitle>
                                    <v-list-item-title>
                                        <h5>
                                            <strong>{{message.text}}</strong>
                                        </h5>
                                    </v-list-item-title>
                                    <v-list-item-subtitle>
                                        <strong>{{message.date}}</strong>
                                    </v-list-item-subtitle>
                                </v-list-item-content>
                            </ul>
                        </div>
                    </li>
                </ul>
            </v-content>
        </v-container>
    </div>
</template>

<script>
    import {AXIOS} from "../../http-common";

    export default {
        props: [],
        data() {
            return {
                messages: [],
                currentChat: JSON.parse(localStorage.getItem('currentChat')),
                companionId: 0
            }
        },
        created: function () {
            AXIOS.get('/message/' + this.currentChat.chatId).then((response) => {
                this.messages = response.data;
                console.log(response.data);
            }).catch(error => console.log(error));
            this.scrollToEnd();
            if (this.currentChat.isExecutor) {
                this.companionId = this.currentChat.customerId
                localStorage.setItem('myId', this.currentChat.executorId)
            } else {
                this.companionId = this.currentChat.executorId
                localStorage.setItem('myId', this.currentChat.customerId)
            }
            this.scrollToEnd();
        },

        methods: {
            scrollToEnd: function () {
                this.$nextTick(() => {
                    var container = this.$el.querySelector('.messages')
                    container.scrollTop = container.scrollHeight
                })
            },
        }
    }
</script>

<style>
    span.emoji {
        font-size: 20px;
        vertical-align: middle;
        line-height: 2;
    }

    .link {
        color: white;
        text-decoration: none;
    }

    .companion {
        position: relative;
        font-family: sans-serif;
        font-size: 18px;
        line-height: 24px;
        width: 500px;
        border-radius: .4em;
        background: #aff5d4;
        text-align: left;
        margin-bottom: 10px;
        min-height: 0;
        /*margin-left: auto;*/
    }


    .companion:after {
        content: '';
        position: absolute;
        left: 0;
        top: 50%;
        width: 0;
        height: 0;
        border: 20px solid transparent;
        border-right-color: #aff5d4;
        border-left: 0;
        border-bottom: 0;
        margin-top: -10px;
        margin-left: -20px;
        min-height: 0;
    }

    .speech-bubble {
        position: relative;
        font-family: sans-serif;
        font-size: 18px;
        line-height: 24px;
        background: #bee5eb;
        width: 500px;
        border-radius: .4em;
        text-align: right;
        margin-bottom: 10px;
        margin-left: auto;
        padding-right: 24px;
    }


    .speech-bubble:after {
        content: '';
        position: absolute;
        right: 0;
        top: 50%;
        width: 0;
        height: 0;
        border: 20px solid transparent;
        border-left-color: #bee5eb;
        border-right: 0;
        border-bottom: 0;
        margin-top: -10px;
        margin-right: -20px;
        max-height: 0;
    }



</style>
