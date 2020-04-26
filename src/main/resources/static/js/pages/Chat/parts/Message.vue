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
        <v-sheet>
            <v-container>
                <v-content>

                    <ul class="list-group">
                        <li class="list-group-item"
                            v-for="(message,index) in messages"
                            :key="index"
                        >
                            <v-list-item>
                                <v-list-item-content>
                                    <v-list-item-title>
                                        <strong>Сообщение: {{message.text}}</strong>
                                    </v-list-item-title>
                                    <v-list-item-subtitle>
                                        <strong>Отправитель: {{message.author.name+" "+message.author.surname}}</strong>
                                    </v-list-item-subtitle>
                                    <v-list-item-subtitle>
                                        <strong>Дата: {{message.date}}</strong>
                                    </v-list-item-subtitle>
                                </v-list-item-content>
                            </v-list-item>
                        </li>
                    </ul>
                </v-content>
            </v-container>
        </v-sheet>


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
            } else {
                this.companionId = this.currentChat.executorId
            }
            this.scrollToEnd();
        },

        methods: {
            scrollToEnd: function () {
                this.$nextTick(() => {
                    var container = this.$el.querySelector('list-group-item')
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
</style>
