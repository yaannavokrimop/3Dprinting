<template>
        <v-row no-gutters>
            <v-col sm="2" class="scrollable">
                <responses></responses>
            </v-col>
            <v-col sm="10" style="position: relative;">
                <div class="chat-container" ref="chatContainer">
                    <message :messages="messages" @imageLoad="scrollToEnd"></message>
                </div>
                <emoji-picker :show="emojiPanel" @close="toggleEmojiPanel" @click="addEmojiToMessage"></emoji-picker>
                <div class="typer">
                    <input type="text" placeholder="Напишите сообщение..." v-on:keyup.enter="sendMessage"
                           v-model="content">
                    <v-btn icon class="blue--text emoji-panel" @click="toggleEmojiPanel">
                        <v-icon>mdi-emoticon-outline</v-icon>
                    </v-btn>
                </div>
            </v-col>
        </v-row>
</template>

<script>
    import Message from './parts/Message.vue'
    import EmojiPicker from './parts/EmojiPicker.vue'
    import Responses from './parts/ChatResponses.vue'
    import {AXIOS} from "../http-common";

    export default {
        data() {
            return {
                content: '',
                messages: [],
                emojiPanel: false,
                currentRef: {},
                loading: false,
                totalChatHeight: 0,
                currentChat: JSON.parse(localStorage.getItem('currentChat'))
            }
        },
        props: [
            'id'
        ],
        mounted() {
            this.scrollToEnd();
        },
        components: {
            'message': Message,
            'emoji-picker': EmojiPicker,
            'responses': Responses
        },
        methods: {
            sendMessage() {
                if (this.content !== '') {
                    let newMessage = {
                        'text': this.$data.content,
                        'chatId': this.currentChat.chatId
                    };

                    AXIOS.post('message', newMessage).then((response) => {
                        console.log(response);
                        this.scrollToEnd();
                        this.successAlert();
                    }).catch(error => console.log(error));
                }
            },
            successAlert() {
                this.content = '';
                location.reload();
            },
            scrollToEnd: function () {
                this.$nextTick(() => {
                    this.totalChatHeight = this.$refs.chatContainer.scrollHeight;
                    var container = this.$el.querySelector('.chat-container')
                    container.scrollTop = container.scrollHeight
                })
            },
            addEmojiToMessage(emoji) {
                this.content += emoji.value
            },

            toggleEmojiPanel() {
                this.emojiPanel = !this.emojiPanel
            }
        },
        created: function () {
            this.scrollToEnd()
        }
    }
</script>

<style>
    .scrollable {
        overflow-y: auto;
        height: 90vh;
    }

    .typer {
        box-sizing: border-box;
        display: flex;
        align-items: center;
        bottom: 0;
        height: 4.9rem;
        width: 100%;
        background-color: #fff;
        box-shadow: 0 -5px 10px -5px rgba(0, 0, 0, .2);
    }

    .typer input[type=text] {
        position: absolute;
        left: 2.5rem;
        padding: 1rem;
        width: 80%;
        background-color: transparent;
        border: none;
        outline: none;
        font-size: 1.25rem;
    }

    .chat-container {
        box-sizing: border-box;
        height: calc(100vh - 9.5rem);
        overflow-y: auto;
        padding: 10px;
        background-color: #f2f2f2;
    }

    .message {
        margin-bottom: 3px;
    }

    .message.own {
        text-align: right;
    }

    .message.own .content {
        background-color: lightskyblue;
    }

    .chat-container .username {
        font-size: 18px;
        font-weight: bold;
    }

    .chat-container .content {
        padding: 8px;
        background-color: lightgreen;
        border-radius: 10px;
        display: inline-block;
        box-shadow: 0 1px 3px 0 rgba(0, 0, 0, 0.2), 0 1px 1px 0 rgba(0, 0, 0, 0.14), 0 2px 1px -1px rgba(0, 0, 0, 0.12);
        max-width: 50%;
        word-wrap: break-word;
    }

    @media (max-width: 480px) {
        .chat-container .content {
            max-width: 60%;
        }
    }

</style>
