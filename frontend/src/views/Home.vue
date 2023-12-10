<template>
    <div class="home">
        <HelloWorld msg="Welcome to Your Vue.js App" />

        <label>Type your name:</label>
        <input type="text" v-model="name" />
        <br />
        <b-button variant="primary" size="sm" v-on:click="submit"
            >Submit</b-button
        >
    </div>
</template>

<script>
// import component to use it as HTML element
import HelloWorld from "@/components/HelloWorld.vue";
// import JS method to use in this view
import { showToastMessage } from "../js/util";

export default {
    name: "Home",
    components: {
        HelloWorld
    },
    data() {
        return {
            // this variable is assigned to the input text element
            name: ""
        };
    },
    methods: {
        // this function is assigned to the button
        submit: function () {
            showToastMessage(
                this,
                "Welcome!",
                `Hello ${this.name}! Nice to meet you!`,
                "success"
            );
            this.name = "";
        }
    }
};
</script>

<style>
.home label {
    margin-right: 10px;
}
</style>
