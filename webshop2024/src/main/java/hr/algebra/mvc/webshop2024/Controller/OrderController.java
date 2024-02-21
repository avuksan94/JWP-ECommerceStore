package hr.algebra.mvc.webshop2024.Controller;

import hr.algebra.bl.webshop2024bl.Service.*;
import hr.algebra.dal.webshop2024dal.Consts.WebShopConsts;
import hr.algebra.dal.webshop2024dal.Entity.*;
import hr.algebra.dal.webshop2024dal.Enum.PaymentType;
import hr.algebra.mvc.webshop2024.ViewModel.CartItemVM;
import hr.algebra.mvc.webshop2024.ViewModel.OrderItemVM;
import hr.algebra.mvc.webshop2024.ViewModel.OrderVM;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.math.BigDecimal;
import java.security.Principal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Controller
@RequestMapping("webShop")
public class OrderController {
    private final OrderService orderService;
    private final OrderItemService orderItemService;
    private final ProductService productService;
    private final ShoppingCartService shoppingCartService;
    private final CartItemService cartItemService;
    private final ProductImageService productImageService;

    public OrderController(OrderService orderService, OrderItemService orderItemService, ProductService productService, ShoppingCartService shoppingCartService, CartItemService cartItemService, ProductImageService productImageService) {
        this.orderService = orderService;
        this.orderItemService = orderItemService;
        this.productService = productService;
        this.shoppingCartService = shoppingCartService;
        this.cartItemService = cartItemService;
        this.productImageService = productImageService;
    }

    @PostMapping("/order/finalize")
    public String orderProducts(Principal principal,@RequestParam("paymentMethod") PaymentType paymentMethod) {
        if (principal == null) {
            return "redirect:/webShop/security/login";
        }

        Optional<ShoppingCart> shoppingCartOpt = shoppingCartService.findByUsername(principal.getName());
        if (!shoppingCartOpt.isPresent()) {
            return "redirect:/webShop/products/list";
        }

        List<CartItem> cartItems = cartItemService.findByShoppingCart(shoppingCartOpt.get());
        if (cartItems.isEmpty()) {
            return "redirect:/webShop/products/list";
        }

        Order order = createOrder(principal, calculateTotalAmount(cartItems),paymentMethod);
        orderService.save(order);

        List<OrderItem> orderItems = createOrderItems(cartItems, order);
        orderItems.forEach(orderItemService::save);

        //I want this removed when the order is finished
        cartItems.forEach(cartItem -> cartItemService.deleteById(cartItem.getCartItemId()));
        shoppingCartService.deleteById(shoppingCartOpt.get().getCartId());

        return "redirect:/webShop/products/list";
    }

    private Order createOrder(Principal principal, BigDecimal totalAmount,PaymentType paymentMethod) {
        Order order = new Order();
        order.setUsername(principal.getName());
        order.setPaymentMethod(paymentMethod.name());
        order.setPurchaseDate(LocalDateTime.now());
        order.setTotalAmount(totalAmount);
        return order;
    }

    private BigDecimal calculateTotalAmount(List<CartItem> cartItems) {
        return cartItems.stream()
                .map(item -> item.getProduct().getPrice().multiply(BigDecimal.valueOf(item.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    private List<OrderItem> createOrderItems(List<CartItem> cartItems, Order order) {
        return cartItems.stream().map(cartItem -> {
            OrderItem orderItem = new OrderItem();
            orderItem.setOrder(order);
            orderItem.setProduct(cartItem.getProduct());
            orderItem.setQuantity(cartItem.getQuantity());
            orderItem.setPrice(cartItem.getProduct().getPrice());
            return orderItem;
        }).collect(Collectors.toList());
    }

    @GetMapping("/order/forShopper")
    public String viewOrders(Model model, Principal principal) {
        if (principal == null) {
            return "redirect:/webShop/security/login";
        }

        List<Order> orders = orderService.findAllByUsername(principal.getName());

        List<OrderVM> userOrders = new ArrayList<>();

        for (Order order : orders) {
            List<OrderItem> realOrderItems = orderItemService.findByOrder(order);
            List<OrderItemVM> orderItems = new ArrayList<>();
            List<ProductImage> productImages = productImageService.findAll();

            for (OrderItem realOrderItem : realOrderItems) {
                OrderItemVM orderItemVM = new OrderItemVM();
                String imageLink = WebShopConsts.DEFAULT_IMAGE_FILENAME;

                for (ProductImage image : productImages) {
                    if (Objects.equals(image.getProduct().getProductId(), realOrderItem.getProduct().getProductId())) {
                        imageLink = image.getImage().getImageUrl();
                        break;
                    }
                }

                orderItemVM.setOrderItemId(realOrderItem.getOrderItemId());
                orderItemVM.setOrderId(realOrderItem.getOrder().getOrderId());
                orderItemVM.setProductId(realOrderItem.getProduct().getProductId());
                orderItemVM.setProductName(realOrderItem.getProduct().getName());
                orderItemVM.setProductPrice(realOrderItem.getProduct().getPrice());
                orderItemVM.setQuantity(realOrderItem.getQuantity());
                orderItemVM.setImageUrls(imageLink);

                orderItems.add(orderItemVM);
            }

            OrderVM userOrderVM = new OrderVM();
            userOrderVM.setOrder(order);
            userOrderVM.setOrderItems(orderItems);
            userOrders.add(userOrderVM);
        }
        model.addAttribute("userOrders", userOrders);

        return "orders/list-orders";
    }

    @GetMapping("/admin/order/allOrders")
    public String viewAllOrders(Model model) {
        List<Order> orders = orderService.findAll();

        List<OrderVM> userOrders = new ArrayList<>();

        for (Order order : orders) {
            List<OrderItem> realOrderItems = orderItemService.findByOrder(order);
            List<OrderItemVM> orderItems = new ArrayList<>();
            List<ProductImage> productImages = productImageService.findAll();

            for (OrderItem realOrderItem : realOrderItems) {
                OrderItemVM orderItemVM = new OrderItemVM();
                String imageLink = WebShopConsts.DEFAULT_IMAGE_FILENAME;

                for (ProductImage image : productImages) {
                    if (Objects.equals(image.getProduct().getProductId(), realOrderItem.getProduct().getProductId())) {
                        imageLink = image.getImage().getImageUrl();
                        break;
                    }
                }

                orderItemVM.setOrderItemId(realOrderItem.getOrderItemId());
                orderItemVM.setOrderId(realOrderItem.getOrder().getOrderId());
                orderItemVM.setProductId(realOrderItem.getProduct().getProductId());
                orderItemVM.setProductName(realOrderItem.getProduct().getName());
                orderItemVM.setProductPrice(realOrderItem.getProduct().getPrice());
                orderItemVM.setQuantity(realOrderItem.getQuantity());
                orderItemVM.setImageUrls(imageLink);

                orderItems.add(orderItemVM);
            }

            OrderVM userOrderVM = new OrderVM();
            userOrderVM.setOrder(order);
            userOrderVM.setOrderItems(orderItems);
            userOrders.add(userOrderVM);
        }
        model.addAttribute("userOrders", userOrders);

        return "orders/list-orders-admin";
    }

    @GetMapping("/admin/order/orderByUsernameAndDates")
    public String searchOrders(Model model, @RequestParam String username,
                               @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
                               @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {
        LocalDateTime dateTimeAtStart = startDate.atStartOfDay();
        LocalDateTime dateTimeAtEnd = endDate.atStartOfDay();
        List<Order> orders = orderService.findByUsernameAndPurchaseDateBetween(username,dateTimeAtStart,dateTimeAtEnd);

        List<OrderVM> userOrders = new ArrayList<>();

        for (Order order : orders) {
            List<OrderItem> realOrderItems = orderItemService.findByOrder(order);
            List<OrderItemVM> orderItems = new ArrayList<>();
            List<ProductImage> productImages = productImageService.findAll();

            for (OrderItem realOrderItem : realOrderItems) {
                OrderItemVM orderItemVM = new OrderItemVM();
                String imageLink = WebShopConsts.DEFAULT_IMAGE_FILENAME;

                for (ProductImage image : productImages) {
                    if (Objects.equals(image.getProduct().getProductId(), realOrderItem.getProduct().getProductId())) {
                        imageLink = image.getImage().getImageUrl();
                        break;
                    }
                }

                orderItemVM.setOrderItemId(realOrderItem.getOrderItemId());
                orderItemVM.setOrderId(realOrderItem.getOrder().getOrderId());
                orderItemVM.setProductId(realOrderItem.getProduct().getProductId());
                orderItemVM.setProductName(realOrderItem.getProduct().getName());
                orderItemVM.setProductPrice(realOrderItem.getProduct().getPrice());
                orderItemVM.setQuantity(realOrderItem.getQuantity());
                orderItemVM.setImageUrls(imageLink);

                orderItems.add(orderItemVM);
            }

            OrderVM userOrderVM = new OrderVM();
            userOrderVM.setOrder(order);
            userOrderVM.setOrderItems(orderItems);
            userOrders.add(userOrderVM);
        }
        model.addAttribute("userOrders", userOrders);

        return "orders/list-orders-admin";
    }
}
