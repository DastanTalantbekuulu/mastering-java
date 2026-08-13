<template>
  <div class="container">
    <h2>Список ожидания</h2>

    <div class="tabs">
      <button
          :class="{ active: activeTab === 'clients' }"
          @click="switchTab('clients')"
      >
        Клиенты
      </button>
      <button
          :class="{ active: activeTab === 'staffs' }"
          @click="switchTab('staffs')"
      >
        Сотрудники
      </button>
      <input
          type="text"
          v-model="searchQuery"
          placeholder="Поиск по имени или фамилии"
          class="search-input"
      />
    </div>

    <div v-if="loading" class="loading-overlay">
      <div class="spinner"></div>
      <p>Запрос обрабатывается...</p>
    </div>


    <div class="content">
      <div class="list">
        <h3 v-if="activeTab === 'clients'">Клиенты</h3>
        <h3 v-if="activeTab === 'staffs'">Сотрудники</h3>
        <ul>
          <li
              v-for="(person, index) in filteredList"
              :key="person.id"
              @click="setActivePerson(person, index)"
              :class="{ active: index === currentIndex }"
          >
            {{ person.firstName }} {{ person.lastName }} - {{ person.role }}
          </li>
        </ul>
      </div>

      <div class="details" v-if="currentPerson">
        <h3>Детали</h3>
        <p><strong>Полное имя:</strong> {{ currentPerson.firstName }} {{ currentPerson.lastName }}</p>
        <p><strong>Email:</strong> {{ currentPerson.email }}</p>
        <p><strong>Роль:</strong> {{ currentPerson.role }}</p>
        <div class="d-flex gap-2 mt-3">
          <router-link :to="'/users/view/' + currentPerson.id" class="badge badge-info p-2 rounded">
            Подробнее
          </router-link>
        </div>

        <div class="buttons">
          <button class="approve" @click="approvePerson(currentPerson.id)">Подтвердить</button>
          <button class="reject" @click="rejectPerson(currentPerson.id)">Отклонить</button>
        </div>
      </div>

      <div class="details placeholder" v-else>
        <p>Выберите пользователя для просмотра деталей...</p>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, computed, onMounted } from "vue";
import WaitingListService from "@/services/WaitingListService";

const clients = ref([]);
const staffs = ref([]);
const activeTab = ref("clients");
const currentPerson = ref(null);
const currentIndex = ref(-1);
const searchQuery = ref("");
const loading = ref(false);

const fetchClients = async () => {
  try {
    const clientRes = await WaitingListService.getClients();
    clients.value = clientRes.data;
  } catch (error) {
    console.error("Ошибка при загрузке клиентов:", error);
  }
};

const fetchStaffs = async () => {
  try {
    const staffRes = await WaitingListService.getEmployees();
    staffs.value = staffRes.data;
  } catch (error) {
    console.error("Ошибка при загрузке сотрудников:", error);
  }
};

const switchTab = (tab) => {
  activeTab.value = tab;
  if (tab === "clients" && clients.value.length === 0) {
    fetchClients();
  }
  if (tab === "staffs" && staffs.value.length === 0) {
    fetchStaffs();
  }
};

const setActivePerson = (person, index) => {
  currentPerson.value = person;
  currentIndex.value = index;
};

const approvePerson = async (id) => {
  loading.value = true;
  try {
    await WaitingListService.approvePerson(id);
    alert("Сотрудник успешно подтвержден!");

    if (activeTab.value === "clients") {
      await fetchClients();
    } else {
      await fetchStaffs();
    }

    currentPerson.value = null;
    currentIndex.value = -1;
  } catch (error) {
    console.error("Ошибка при одобрении:", error);
  } finally {
    loading.value = false;
  }
};

const rejectPerson = async (id) => {
  loading.value = true;
  try {
    await WaitingListService.rejectPerson(id);
    alert("Сотрудник успешно отклонен!");

    if (activeTab.value === "clients") {
      await fetchClients();
    } else {
      await fetchStaffs();
    }

    currentPerson.value = null;
    currentIndex.value = -1;
  } catch (error) {
    console.error("Ошибка при отклонении:", error);
  } finally {
    loading.value = false;
  }
};

onMounted(fetchClients);

const activeList = computed(() => {
  return activeTab.value === "clients" ? clients.value : staffs.value;
});

const filteredList = computed(() => {
  if (!searchQuery.value) {
    return activeList.value;
  }

  const query = searchQuery.value.trim().toLowerCase();
  return activeList.value.filter(person => {
    const fullName = `${person.firstName} ${person.lastName}`.toLowerCase();
    return fullName.includes(query);
  });
});
</script>

<style scoped>
.container {
  padding: 20px;
}

.tabs {
  display: flex;
  gap: 10px;
  margin-bottom: 20px;
}

.tabs button {
  padding: 10px 20px;
  border: none;
  cursor: pointer;
  background-color: #ddd;
  transition: background 0.3s;
  border-radius: 8px;
}

.tabs button.active {
  background-color: #4caf50;
  color: white;
  font-weight: bold;
  border-radius: 8px;
}

.search-input {
  flex: 1;
  padding: 10px;
  border: 1px solid #ccc;
  border-radius: 8px;
  font-size: 16px;
}

.content {
  display: flex;
  gap: 20px;
}

.list {
  width: 40%;
  border-right: 1px solid #ddd;
  padding-right: 20px;
}

.list ul {
  list-style: none;
  padding: 0;
}

.list li {
  padding: 10px;
  border-bottom: 1px solid #ddd;
  cursor: pointer;
}

.list li.active {
  background-color: #f0f0f0;
  font-weight: bold;
}

.details {
  width: 60%;
}

.details.placeholder {
  display: flex;
  align-items: center;
  justify-content: center;
  color: gray;
  font-size: 18px;
}

.buttons {
  margin-top: 20px;
}

button.approve {
  background-color: #4caf50;
  color: white;
  padding: 10px 15px;
  border: none;
  cursor: pointer;
  border-radius: 8px;
}

button.reject {
  background-color: #f44336;
  color: white;
  padding: 10px 15px;
  border: none;
  cursor: pointer;
  margin-left: 10px;
  border-radius: 8px;
}

.loading-overlay {
  position: fixed;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  background-color: rgba(0, 0, 0, 0.6);
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  color: white;
  font-size: 20px;
  font-weight: bold;
  z-index: 9999;
  opacity: 0;
  animation: fade-in 0.3s ease-in-out forwards;
}

@keyframes fade-in {
  from {
    opacity: 0;
  }
  to {
    opacity: 1;
  }
}

.spinner {
  width: 60px;
  height: 60px;
  border: 6px solid rgba(255, 255, 255, 0.3);
  border-radius: 50%;
  border-top: 6px solid #fff;
  animation: spin 1s linear infinite;
  margin-bottom: 15px;
}

@keyframes spin {
  0% { transform: rotate(0deg); }
  100% { transform: rotate(360deg); }
}

button.approve, button.reject {
  transition: background 0.2s ease-in-out, filter 0.2s ease-in-out;
}

button.approve:hover, button.reject:hover {
  filter: brightness(0.9);
}

</style>
