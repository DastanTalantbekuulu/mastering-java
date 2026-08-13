<template>
    <div class="logs-container">
      <h4>Client Logs</h4>
      <div class="input-group mb-3">
        <input type="datetime-local" v-model="startDate" class="form-control" placeholder="Start Date" />
        <input type="datetime-local" v-model="endDate" class="form-control" placeholder="End Date" />
        <button class="btn btn-outline-secondary" @click="fetchLogs">Search</button>
        <button class="btn btn-outline-success" @click="downloadLogs">Download</button>
      </div>
      <ul class="list-group logs-list">
        <li v-for="log in logs" :key="log.id" class="list-group-item">
          <strong>{{ log.timestamp }}</strong> - {{ log.message }}
        </li>
      </ul>
      <p v-if="logs.length === 0">No logs found.</p>
    </div>
</template>

<script>

</script>