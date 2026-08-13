<template> 
    <div>
      <h2>Worker Details</h2>
      <div v-if="registrar">
        <div><strong>ID:</strong> {{ registrar.id }}</div>
        <div><strong>Full Name:</strong> {{ registrar.fullName }}</div>
        <div><strong>Gender:</strong> {{ registrar.personGender || "Not specified" }}</div>
        <div><strong>Nationality:</strong> {{ registrar.nationality || "Not specified" }}</div>
        <div><strong>Date of Birth:</strong> {{ registrar.dateOfBirth || "N/A" }}</div>
        <div><strong>Identification Number:</strong> {{ registrar.identificationNumber || "N/A" }}</div>
        <div><strong>Document ID:</strong> {{ registrar.documentId }}</div>
        <div><strong>Issuing Authority:</strong> {{ registrar.issuingAuthority || "N/A" }}</div>
        <div><strong>Status ID:</strong> {{ registrar.statusId || "N/A" }}</div>
      </div>
      <div v-else>
        <p>Loading worker details...</p>
      </div>
      <button class="btn btn-secondary" @click="$router.push('/workers')">
        Back to List
      </button>
    </div>
  </template>
  
  <script>
  import RegistrarDataService from "../services/RegistrarDataService";
  
  export default {
    name: "ViewWorker",
    data() {
      return {
        registrar: null,
      };
    },
    methods: {
      fetchRegistrarDetails() {
        const registrarId = this.$route.params.id;
        RegistrarDataService.getRegistrarById(registrarId)
          .then((response) => {
            this.registrar = response.data;
          })
          .catch((e) => {
            console.error("Error fetching registrar details:", e);
          });
      },
    },
    mounted() {
      this.fetchRegistrarDetails();
    },
  };
  </script>
  