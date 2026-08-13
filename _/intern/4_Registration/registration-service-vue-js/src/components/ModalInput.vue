<template>
  <transition name="modal">
    <div v-if="show" class="modal-mask" @click.self="closeModal">
      <div class="modal-container input-group ">
        <input :type="inputType" v-model="filterValue" :placeholder="placeholderText" class="form-control" ref="inputField"/>
        <button type="button" class="btn btn-primary " @click="applyFilter">
          <Check></Check>
        </button>
        <button type="button" class="btn btn-danger " @click="closeModal">
          <Close></Close>
        </button>
      </div>
    </div>
  </transition>
</template>

<script>
import Close from "./icon/Close.vue";
import Check from "@/components/icon/Check.vue";

export default {
  components: {Close, Check},

  props: {
    show: {
      type: Boolean,
      required: true,
    },
    placeholderText: {
      type: String,
      default: "",
    },
    initialFilterValue: {
      type: String,
      default: "",
    },
    inputType: {
      type: String,
      default: "search",
    },
  },
  emits: ["apply", "close"],
  data() {
    return {
      filterValue: this.initialFilterValue,
    };
  },
  watch: {
    initialFilterValue(newValue) {
      this.filterValue = newValue;
    },
    show(newVal) {
      if (newVal) {
        this.$nextTick(() => {
          this.resetFilterValue();
          this.$refs.inputField.focus();
        });
      }
    },
  },
  methods: {
    applyFilter() {
      this.$emit("apply", this.filterValue);
      this.closeModal();
    },
    closeModal() {
      this.$emit("close");
    },
    resetFilterValue() {
      this.filterValue = "";
    },
  },
};
</script>

<style scoped>
.modal-mask {
  position: fixed;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  background-color: rgba(0, 0, 0, 0.5);
  display: flex;
  justify-content: center;
  align-items: center;
  z-index: 1000;
}

.modal-wrapper {
  display: flex;
  justify-content: center;
  align-items: center;
}

.modal-container {
  background-color: white;
  padding: 20px;
  border-radius: 8px;
  width: 500px;
  box-shadow: 0 2px 10px rgba(0, 0, 0, 0.1);
}

.modal-header h3 {
  margin: 0;
}

.modal-body {
  margin: 20px 0;
}

.modal-footer {
  display: flex;
  justify-content: flex-end;
  gap: 10px;
}

.btn {
  padding: 8px 16px;
  border-radius: 4px;
  cursor: pointer;
}

.btn-primary {
  background-color: #007bff;
  color: white;
  border: none;
}

.btn-primary:hover {
  background-color: #0056b3;
}

.btn-secondary {
  background-color: #6c757d;
  color: white;
  border: none;
}

.btn-secondary:hover {
  background-color: #5a6268;
}
</style>