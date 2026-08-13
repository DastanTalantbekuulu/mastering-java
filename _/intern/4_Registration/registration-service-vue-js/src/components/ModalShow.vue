<template>
  <div class="modal-overlay" v-if="show" @click.self="close">
    <div class="modal-content">
      <button class="close-button" @click="close">
        <Close/>
      </button>
      <slot></slot>
    </div>
  </div>
</template>

<script>
import Close from "./icon/Close.vue";

export default {
  components: { Close },
  props: {
    show: {
      type: Boolean,
      required: true,
    },
  },
  methods: {
    close() {
      this.$emit("close");
    },
  },
};
</script>

<style scoped>
.modal-overlay {
  position: fixed;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  background: rgba(0, 0, 0, 0.5);
  display: flex;
  justify-content: center;
  align-items: center;
}

.modal-content {
  position: relative;
  background: white;
  padding: 20px;
  border-radius: 8px;
  max-width: 500px;
  width: 100%;
}

.close-button {
  position: absolute;
  top: 10px;
  right: 10px;
  background: red;
  color: white;
  border: none;
  width: 32px;
  height: 32px;
  display: flex;
  justify-content: center;
  align-items: center;
  border-radius: 4px;
  cursor: pointer;
}

.close-button:hover {
  background: darkred;
}
</style>
