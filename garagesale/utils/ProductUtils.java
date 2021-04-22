package com.endava.garagesale.utils;

import com.endava.garagesale.entity.Order;
import com.endava.garagesale.entity.Product;
import com.endava.garagesale.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * This class deals with handling products
 */
@Component
public class ProductUtils {
    private final ProductRepository productRepository;
    private final OrderUtils orderUtils;

    @Autowired
    public ProductUtils(ProductRepository productRepository, OrderUtils orderUtils) {
        this.productRepository = productRepository;
        this.orderUtils = orderUtils;
    }

    /**
     * @param orderId: Long
     * @return: the IDs of the purchased wanted to be purchased
     */
    public List<Long> getProductIds(Long orderId) {
        List<Long> idList = new ArrayList<>();
        Order order = orderUtils.getOrderById(orderId);
        for (Product product : order.getProductList()) {
            idList.add(product.getId());
        }
        return idList;
    }

    /**
     * @param id: Long
     * @return: the category of a product, given its id
     */
    public Product.Category getCategoryById(Long id) {
        return this.getProductById(id).getCategory();
    }

    /**
     * @param idList: List<Long>
     * @return: true, if there are products from the same category, false otherwise
     */
    public boolean isFromTheSameCategory(List<Long> idList) {
        for (int i = 0; i < idList.size() - 1; i++)
            for (int j = i + 1; j < idList.size(); j++)
                if (this.getCategoryById(idList.get(i)).equals(this.getCategoryById(idList.get(j))))
                    return true;
        return false;
    }

    /**
     * @param id
     * @return: returns a product by id
     */
    public Product getProductById(Long id) {
        for (Product product : this.getAvailableProducts()) {
            if (product.getId().equals(id)) {
                return product;
            }
        }
        return null;
    }

    /**
     * @param orderId: Long
     * @return: the names of the purchased products
     */
    public List<String> getPurchasedProductsNames(Long orderId) {
        List<String> purchasedProductsNames = new ArrayList<>();
        for (Long id : this.getProductIds(orderId)) {
            purchasedProductsNames.add(this.getProductById(id).getName());
        }
        return purchasedProductsNames;
    }

    /**
     * @param idList
     * @return: the purchased products
     */
    public List<Product> getPurchasedProducts(List<Long> idList) {
        List<Product> purchasedProducts = new ArrayList<>();
        for (Long id : idList) {
            purchasedProducts.add(this.getProductById(id));
        }
        return purchasedProducts;
    }

    /**
     * @return: the products on stock
     */
    public List<Product> getAvailableProducts() {
        List<Product> products = new ArrayList<>();
        for (Product product : productRepository.findAll()) {
            if (product.getNrOfItems() > 0) {
                products.add(product);
            }
        }
        return products;
    }

    /**
     * @param idList It updates the stock of those products which have been purchased
     */
    public void updateStock(List<Long> idList) {
        for (Long id : idList) {
            if (this.getProductById(id).getNrOfItems() > 0) {
                productRepository.findById(id).ifPresent(asset -> asset.setNrOfItems(asset.getNrOfItems() - 1));
                productRepository.findById(id).ifPresent(productRepository::save);
            }
        }
    }

    /**
     * @param productsIds: List<Long>
     * @return: the sum of an order
     */
    public double getTotalSum(List<Long> productsIds) {
        double sum = 0;
        for (Long id : productsIds) {
            sum += this.getProductById(id).getPrice();
        }
        return sum;
    }

}
