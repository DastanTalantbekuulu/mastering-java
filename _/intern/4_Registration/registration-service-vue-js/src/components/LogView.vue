<template>
  <div>
    <div>
      <div class="btn-group">
        <label class="btn btn-outline-primary" :class="{ 'active': type === 'version' }" @click="show('version')">
          Версия
        </label>
        <label class="btn btn-outline-primary" :class="{ 'active': type === 'login' }" @click="show('login')">
          Журнал
        </label>
      </div>
    </div>
    <div class="row">
      <div class="col-6">
        <div class="input-group">
          <span class="input-group-text">от:</span>
          <input type="datetime-local" step="1" class="form-control" v-model="fromDate"/>
          <span class="input-group-text">до:</span>
          <input type="datetime-local" step="1" class="form-control" v-model="toDate"/>
        </div>
      </div>
      <div class="col-3">
        <button type="button" class="ml-3 btn btn-primary mx-3" @click="applyFilters">
          <Check></Check>
        </button>
        <button v-if="showResetButton" class="btn btn-danger  mx-3" @click="resetFilters">
          <Close></Close>
        </button>
      </div>
    </div>
    <div v-if="type === 'version'">
      <table class="table  table-hover mt-1">
        <thead class="table-secondary">
        <tr class="table-secondary">
          <td style="width: 50px">#</td>
          <th class="td-clickable" @click="handleSort('identifier')">
            ID
            <span v-if="sortBy === 'identifier'">{{ sortAsc ? "▲" : "▼" }}</span>
          </th>
          <th class="td-clickable" @click="handleSort('datetime')">
            Время
            <span v-if="sortBy === 'datetime'">{{ sortAsc ? "▲" : "▼" }}</span>
          </th>
          <th class="td-clickable" @click="handleSort('user')">
            Пользователь
            <span v-if="sortBy === 'user'">{{ sortAsc ? "▲" : "▼" }}</span>
          </th>
          <th class="td-clickable" @click="handleSort('event')">
            Событие
            <span v-if="sortBy === 'event'"> {{ sortAsc ? "▲" : "▼" }} </span>
          </th>
          <th class="td-clickable" @click="handleSort('record_name')">
            Запись
            <span v-if="sortBy === 'record_name'">{{ sortAsc ? "▲" : "▼" }}</span>
          </th>
          <th class="td-clickable" @click="handleSort('record_identifier')">
            ID записи
            <span v-if="sortBy === 'record_identifier'">{{ sortAsc ? "▲" : "▼" }}</span>
          </th>
          <th class="td-clickable" @click="handleSort('record_version')">
            Версия
            <span v-if="sortBy === 'record_version'">{{ sortAsc ? "▲" : "▼" }}</span>
          </th>
        </tr>
        <tr class="table-secondary">
          <td class="m-0 p-0"></td>
          <th class="m-0 p-0">
            <div style="width: 70px">
              <input v-model="filters.identifier" type="number" min="1" class="form-control">
            </div>
          </th>
          <th class="m-0 p-0">
            <div>
              <input v-model="filters.datetime" type="datetime-local" class="form-control" step="1">
            </div>
          </th>
          <th class="m-0 p-0">
            <div>
              <input v-model="filters.user" type="search" class="form-control">
            </div>
          </th>
          <th class="m-0 p-0">
            <select v-model="filters.event" class="form-select form-control" aria-label="Event" name="event">>
              <option selected value=null>none</option>
              <option value="INSERT"><span :class="getEventClass('INSERT')">INSERT</span></option>
              <option value="UPDATE"><span :class="getEventClass('UPDATE')">UPDATE</span></option>
              <option value="DELETE"><span :class="getEventClass('DELETE')">DELETE</span></option>
            </select>
          </th>
          <th class="m-0 p-0">
            <div>
              <input v-model="filters.record_name" type="search" class="form-control">
            </div>
          </th>
          <th class="m-0 p-0">
            <div>
              <input v-model="filters.record_identifier" type="number" min="1" class="form-control">
            </div>
          </th>
          <th class="m-0 p-0">
            <div>
              <input v-model="filters.record_version" type="number" min="1" class="form-control">
            </div>
          </th>
        </tr>
        </thead>
        <tbody>
        <tr v-for="(log, index) in logs" :key="log.identifier" @click="rowClickVersion(log.identifier)">
          <td class="table-secondary">{{ currentPage * pageSize + index + 1 }}</td>
          <td>{{ log.identifier }}</td>
          <td>{{ formatLabelDateTime(log.datetime) }}</td>
          <td>{{ log.user }}</td>
          <td><span :class="getEventClass(log.event)">{{ log.event }}</span></td>
          <td>{{ log.record_name }}</td>
          <td>{{ log.record_identifier }}</td>
          <td>{{ log.record_version }}</td>
        </tr>
        </tbody>
      </table>
    </div>
    <div v-if="type === 'login'">
      <table class="table table-hover mt-1">
        <thead class="table-secondary">
        <tr class="table-secondary">
          <td style="width: 50px">#</td>
          <th class="td-clickable" @click="handleSort('identifier')">
            ID
            <span v-if="sortBy === 'identifier'">{{ sortAsc ? "▲" : "▼" }}</span>
          </th>
          <th class="td-clickable" @click="handleSort('datetime')">
            Время
            <span v-if="sortBy === 'datetime'">{{ sortAsc ? "▲" : "▼" }}</span>
          </th>
          <th class="td-clickable" @click="handleSort('user')">
            Пользователь
            <span v-if="sortBy === 'user'">{{ sortAsc ? "▲" : "▼" }}</span>
          </th>
          <th class="td-clickable" @click="handleSort('event')">
            Событие
            <span v-if="sortBy === 'event'"> {{ sortAsc ? "▲" : "▼" }} </span>
          </th>
          <th class="td-clickable" @click="handleSort('ip')">
            IP
            <span v-if="sortBy === 'ip'">{{ sortAsc ? "▲" : "▼" }}</span>
          </th>
          <th class="td-clickable" @click="handleSort('agent')">
            Агент
            <span v-if="sortBy === 'agent'">{{ sortAsc ? "▲" : "▼" }}</span>
          </th>
          <th class="td-clickable" @click="handleSort('code')">
            Код
            <span v-if="sortBy === 'code'">{{ sortAsc ? "▲" : "▼" }}</span>
          </th>
        </tr>
        <tr class="table-secondary">
          <td class="m-0 p-0"></td>
          <th class="m-0 p-0">
            <div style="width: 70px">
              <input v-model="filters.identifier" type="number" min="1" class="form-control">
            </div>
          </th>
          <th class="m-0 p-0">
            <div>
              <input v-model="filters.datetime" type="datetime-local" class="form-control" step="1">
            </div>
          </th>
          <th class="m-0 p-0">
            <div>
              <input v-model="filters.user" type="search" class="form-control">
            </div>
          </th>
          <th class="m-0 p-0">
            <select v-model="filters.event" class="form-select form-control" aria-label="Event" name="event">
              <option selected value=null>none</option>
              <option value="LOGIN"><span :class="getEventClass('LOGIN')">LOGIN</span></option>
              <option value="LOGOUT"><span :class="getEventClass('LOGOUT')">LOGOUT</span></option>
            </select>
          </th>
          <th class="m-0 p-0">
            <div style="width: 150px">
              <input v-model="filters.ip" type="search" class="form-control">
            </div>
          </th>
          <th class="m-0 p-0">
            <div>
              <input v-model="filters.agent" type="search" class="form-control">
            </div>
          </th>
          <th class="m-0 p-0">
            <div style="width: 100px">
              <input v-model="filters.code" type="number" min="200" max="599" class="form-control">
            </div>
          </th>
        </tr>
        </thead>
        <tbody>
        <tr v-for="(log, index) in logs" :key="log.identifier" @click="rowClickLogin(log)">
          <td class="table-secondary">{{ currentPage * pageSize + index + 1 }}</td>
          <td>{{ log.identifier }}</td>
          <td>{{ formatLabelDateTime(log.datetime) }}</td>
          <td>{{ log.user }}</td>
          <td><span :class="getEventClass(log.event)">{{ log.event }}</span></td>
          <td>{{ log.ip }}</td>
          <td>{{ log.agent }}</td>
          <td>{{ log.code }}</td>
        </tr>
        </tbody>
      </table>
    </div>
    <div class="pagination d-flex justify-content-between align-items-center my-5">
      <button class="btn btn-primary" :disabled="currentPage === 0" @click="handlePageChange(currentPage - 1)">
        Предыдущая
      </button>
      <span>Страница {{ currentPage + 1 }} из {{ pageInfo.totalPages }}</span>
      <button class="btn btn-primary" :disabled="(currentPage + 1) >= pageInfo.totalPages"
              @click="handlePageChange(currentPage + 1)">
        Следующая
      </button>
    </div>
  </div>
  <ModalShow v-if="isModalOpen" @close="closeModal" show>
    <h3>Данные</h3>
    <pre>{{ JSON.stringify(modalData, null, 2) }}</pre>
  </ModalShow>
</template>
<script>
import {ref, onMounted, computed} from "vue";
import Close from "@/components/icon/Close.vue";
import Check from "@/components/icon/Check.vue";
import ModalShow from "./ModalShow.vue";
import http from "../http-common";

export default {
  name: "LogTable",
  components: {Close, Check, ModalShow},
  setup() {

    const logs = ref([]);
    const currentPage = ref(0);
    const pageSize = ref(10);
    const sortBy = ref("identifier");
    const sortAsc = ref(false);
    const type = ref("version");
    const filters = ref({
      identifier: null,
      datetime: null,
      user: null,
      event: null,
      record_name: null,
      record_identifier: null,
      record_version: null,
      ip: null,
      agent: null,
      code: null,
    });
    const fromDate = ref();
    const toDate = ref();
    const modal = ref(null);
    const initialFilterValue = ref("");
    const showModal = ref(false);
    const dynamicPlaceholder = ref("");
    const dynamicInputType = ref("search");
    const isModalOpen = ref(false);
    const modalData = ref(null);
    const pageInfo = ref({
      size: 10,
      number: 0,
      totalElements: 0,
      totalPages: 0,
    });

    const fetchLogs = async () => {
      if (!validateDates()) {
        alert("Дата 'до' не может быть раньше даты 'от'. Пожалуйста, исправьте даты.");
        return;
      }
      const formattedFilters = {
        ...filters.value,
        datetime: filters.value.datetime ? formatDateTime(filters.value.datetime) : null
      };
      try {
        const payload = {
          page: currentPage.value,
          size: pageSize.value,
          sortBy: sortBy.value,
          asc: sortAsc.value,
          from: formatDateTime(fromDate.value),
          to: formatDateTime(toDate.value),
          type: type.value,
          filters: formattedFilters
        };
        // alert(JSON.stringify(payload));
        const response = await http.post(
            `/v2/log/page`,
            payload
        );
        logs.value = response.data.content;
        pageInfo.value = response.data.page;
      } catch (error) {
        console.error("Error fetching logs:", error);
      }
    };

    const validateDates = () => {
      if (!fromDate.value || !toDate.value) return true;
      const from = new Date(fromDate.value);
      const to = new Date(toDate.value);
      return to >= from;
    };
    const formatLabelDateTime = (datetime) => {
      if (!datetime) return null;
      const date = new Date(datetime);
      const pad = (n) => String(n).padStart(2, "0");
      return `${date.getFullYear()}-${pad(date.getMonth() + 1)}-${pad(date.getDate())} ${pad(date.getHours())}:${pad(date.getMinutes())}:${pad(date.getSeconds())}`;
    };
    const formatDateTime = (datetime) => {
      if (!datetime) return null;
      const date = new Date(datetime);
      const hasTime = datetime.includes("T") && datetime.includes(":");
      const pad = (n) => String(n).padStart(2, "0");
      if (hasTime) {
        return `${date.getFullYear()}-${pad(date.getMonth() + 1)}-${pad(date.getDate())} ${pad(date.getHours())}:${pad(date.getMinutes())}:${pad(date.getSeconds())}`;
      } else {
        return `${date.getFullYear()}-${pad(date.getMonth() + 1)}-${pad(date.getDate())}`;
      }
    };

    const rowClickVersion = async (identifier) => {
      try {
        const response = await http.get(`/v2/log/${identifier}/record/state`);
        if (response.status === 200) {
          modalData.value = response.data;
          isModalOpen.value = true;
          print("data");
        }
      } catch (error) {
        console.error("Ошибка при получении данных:", error);
      }
    };

    const rowClickLogin = async (log) => {
      modalData.value = {IP: log.ip, Agent: log.agent, Response_code: log.code};
      isModalOpen.value = true;
    }

    const closeModal = () => {
      isModalOpen.value = false;
      modalData.value = null;
    };

    const handleSort = (column) => {
      if (sortBy.value === column) {
        sortAsc.value = !sortAsc.value;
      } else {
        sortBy.value = column;
        sortAsc.value = true;
      }
      currentPage.value = 0;
      fetchLogs();
    };

    const handlePageChange = (page) => {
      if (page < 0 || page > pageInfo.value.totalPages) return;
      currentPage.value = page;
      fetchLogs();
    };

    const applyFilters = () => {
      currentPage.value = 0;
      fetchLogs();
    };

    const resetFilters = () => {
      filters.value.identifier = null;
      filters.value.datetime = null;
      filters.value.user = null;
      filters.value.event = null;
      filters.value.record_name = null;
      filters.value.record_identifier = null;
      filters.value.record_version = null;
      filters.value.ip = null;
      filters.value.agent = null;
      filters.value.code = null;

      fromDate.value = null;
      toDate.value = null;

      currentPage.value = 0;
      fetchLogs();
    };

    onMounted(() => {
      fetchLogs();
    });

    const show = (newView) => {
      type.value = newView === "login" ? "login" : "version";
      sortBy.value = "identifier";
      sortAsc.value = false;
      resetFilters();
    }

    const showResetButton = computed(() => {
      return fromDate.value || toDate.value ||
          filters.value.identifier ||
          filters.value.datetime ||
          filters.value.user ||
          filters.value.event ||
          filters.value.record_name ||
          filters.value.record_identifier ||
          filters.value.record_version ||
          filters.value.ip ||
          filters.value.agent ||
          filters.value.code;
    });

    const print = (log) => {
      console.log(log);
    }

    const getEventClass = (event) => {
      switch (event) {
        case 'INSERT':
          return 'event-insert';
        case 'UPDATE':
          return 'event-update';
        case 'DELETE':
          return 'event-delete';
        case 'LOGIN':
          return 'event-login';
        case 'LOGOUT':
          return 'event-logout';
        default:
          return 'event-default';
      }
    }

    return {
      logs,
      currentPage,
      pageSize,
      sortBy,
      sortAsc,
      fromDate,
      toDate,
      type,
      filters,
      pageInfo,
      showModal,
      modal,
      initialFilterValue,
      dynamicPlaceholder,
      dynamicInputType,
      fetchLogs,
      handleSort,
      handlePageChange,
      applyFilters,
      resetFilters,
      isModalOpen,
      modalData,
      rowClickVersion,
      rowClickLogin,
      closeModal,
      show,
      showResetButton,
      formatDateTime,
      formatLabelDateTime,
      getEventClass,
    };
  },
};

</script>
<style scoped>

.td-clickable:hover {
  background-color: #0dcaf0;
  color: white;
  cursor: pointer;
}

.table-hover tbody tr:hover td {
  background-color: #0dcaf0;
  color: white;
  cursor: pointer;
}

.btn.active {
  background-color: #007bff;
  color: white;
}

.event-legend .legend-item {
  min-width: 150px;
  text-align: center;
  font-weight: bold;
  border: 1px solid #ccc;
}

table {
  empty-cells: show;
  background-color: white;
}

.event-insert {
  color: green;
  background-color: #e8f5e9;
  padding: 2px 5px;
  border-radius: 3px;
}

.event-update {
  color: orange;
  background-color: #fff3e0;
  padding: 2px 5px;
  border-radius: 3px;
}

.event-delete {
  color: red;
  background-color: #ffebee;
  padding: 2px 5px;
  border-radius: 3px;
}

.event-login {
  color: blue;
  background-color: #e3f2fd;
  padding: 2px 5px;
  border-radius: 3px;
}

.event-logout {
  color: purple;
  background-color: #f3e5f5;
  padding: 2px 5px;
  border-radius: 3px;
}

.event-default {
  color: gray;
  background-color: #f5f5f5;
  padding: 2px 5px;
  border-radius: 3px;
}
</style>
