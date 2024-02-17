const { createApp } = Vue;

const app = createApp({
  data() {
   return {
           cartItemCount: 0,
           products: []
       };
  },
  mounted() {
    this.updateCartItemCount();
  },
  methods: {
   loadNavbar() {
           $("#navbar-placeholder").load("/shared/navbar.html", () => {
               this.updateCartItemCount();
           });
   },
   updateCartItemCount() {
       fetch('/webShop/shopping/cartItemCount')
           .then(response => response.json())
           .then(data => {
               this.cartItemCount = data;
           })
           .catch(error => console.error('Error fetching cart item count:', error));
   },
   addItemToCart(productId, quantity = 1) {
       const formData = new FormData();
       formData.append('productId', productId);
       formData.append('quantity',quantity);
       fetch('/webShop/shopping/addToCart', {
           method: 'POST',
           body: formData
       }).then(() => {
           this.updateCartItemCount();
           alert('Item added to cart');
       }).catch(error => {
           console.error('Error adding item to cart:', error);
           alert('Failed to add item to cart');
       });
   }
  }
});

app.mount('#vue-app');