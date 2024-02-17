 const app = Vue.createApp({
      data() {
        return {
          imageLink: ''
        };
      },
      methods: {
        setImageLink(event) {
          this.imageLink = event.target.value;
        },
        resetInput() {
          this.imageLink = '';
        }
      }
    }).mount('#imageManagement');