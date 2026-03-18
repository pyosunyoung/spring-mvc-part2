package hello.itemservice.validation;

import hello.itemservice.domain.item.Item;
import org.junit.jupiter.api.Test;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.Set;

public class BeanValidationTest {

    @Test
    void beanValidation(){
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory(); // validation 공장에서
        Validator validator = factory.getValidator(); // 검증기 꺼냄

        Item item = new Item();
        item.setItemName(" "); //공백
        item.setPrice(0);
        item.setQuantity(10000);

        Set<ConstraintViolation<Item>> violations = validator.validate(item); // 검증 대상을 담음
        for (ConstraintViolation<Item> constraintViolation : violations) {
            System.out.println("violation: " + constraintViolation);
            System.out.println(constraintViolation.getMessage());

        }
    }
}
