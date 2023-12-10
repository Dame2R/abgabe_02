<script setup lang="ts">
import {Alert, Button} from "agnostic-vue";
import {FontAwesomeIcon} from "@fortawesome/vue-fontawesome";
import config from "@/config";
import {showToast, Toast} from "@/ts/toasts";
import {faCheck, faXmark} from "@fortawesome/free-solid-svg-icons";
import {onMounted, type Ref, ref} from "vue";

interface Cat {
  id: number;
  name: string;
  ageInYears: number;
  picUrl: string;
}

const cats: Ref<Cat[]> = ref([]);

function fetchAllCats() {
  fetch(`${config.apiBaseUrl}/cats`)
      .then(response => response.json())
      .then(data => data as Cat[])
      .then(data => {
        console.log(data);
        cats.value = data;
      })
      .catch(error => showToast(new Toast("Error", error, "error", faXmark, 10)));
}

function deleteCat(id: number) {
  // send DELETE request to API to delete a specific cat by ID
  fetch(`${config.apiBaseUrl}/cats/${id}`, {method: "DELETE"})
      .then(() => {
        cats.value = cats.value.filter((cat) => cat.id !== id);
        showToast(new Toast("Alert", `Successfully deleted cat with ID ${id}!`, "success", faCheck, 5));
      })
      .catch(error => showToast(new Toast("Error", error, "error", faXmark, 10)));
}

onMounted(() => fetchAllCats());
</script>

<template>
  <h1>Cats</h1>

  <Alert v-if="cats.length === 0" type="warning">
    No cat available on the server...
  </Alert>
  <div v-else>
    <div class="catBox" v-for="cat in cats" :key="cat.id">
      <h3>{{ cat.name }}</h3>
      <p>Age: {{ cat.ageInYears }} years</p>
      <img v-bind:alt="cat.name" v-bind:src="cat.picUrl" class="catLogo"/>
      <div>
        <Button @click="deleteCat(cat.id)" mode="secondary">
          <FontAwesomeIcon icon="trash"/>
          Delete
        </Button>
      </div>
    </div>
  </div>
</template>

<style scoped>
.catBox {
  padding: 10px;
  margin: 3px;
  border: 1px solid #42b983;
  float: left;
  min-width: 250px;
  text-align: center;
}

.catLogo {
  height: 200px;
  border-radius: 50%;
}

.catBox button {
  margin: 5px;
}
</style>