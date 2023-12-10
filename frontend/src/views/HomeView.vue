<script setup lang="ts">
import {Button} from "agnostic-vue";
import {showToast, Toast} from "@/ts/toasts";

let name = "";

function submit() {
  showToast(new Toast("Welcome!", `Hello ${name}! Nice to meet you!`));
}
</script>

<template>
  <main>
    <h1>Welcome to Your Vue.js App</h1>
    <label class="mbs2 mbe2" >Type your name:</label>
    <br/>
    <input type="text" class="mbs2 mbe2" v-model="name"/>
    <br/>
    <Button class="mbs2 mbe2" @click="submit">Submit</Button>
  </main>
</template>
