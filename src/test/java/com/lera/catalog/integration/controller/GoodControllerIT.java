package com.lera.catalog.integration.controller;

import com.lera.catalog.dto.CreateGoodRequest;
import com.lera.catalog.dto.GetGoodsListRequest;
import com.lera.catalog.dto.GetGoodsListResponse;
import com.lera.catalog.integration.BaseIntegrationTest;
import com.lera.catalog.model.GoodTestModel;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.jdbc.core.DataClassRowMapper;

import java.math.BigDecimal;
import java.util.List;

import static io.restassured.RestAssured.given;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class GoodControllerIT extends BaseIntegrationTest {

    @Test
    @DisplayName("Проверка создания товара")
    public void createGoodTest() {
        //when
        given()
                .contentType(ContentType.JSON)
                .body(
                        new CreateGoodRequest(
                                "pizza",
                                "tasty pizza",
                                BigDecimal.valueOf(100)
                        )
                )
                .when()
                .post("/goods/createGood")
                .then()
                .statusCode(200)
                .body("id", equalTo(1));
        //then
        var good = jdbcTemplate.query("select * from good", new DataClassRowMapper<>(GoodTestModel.class)).getFirst();

        assertThat(good.getId()).isEqualTo(1);
        assertThat(good.getName()).isEqualTo("pizza");
        assertThat(good.getDescription()).isEqualTo("tasty pizza");
        assertThat(good.getPrice()).isEqualTo(new BigDecimal("100.00"));
    }

    @Test
    @DisplayName("Проверка возврата списка товаров по списку ids")
    public void getGoodsListTest() {
        //given
        jdbcTemplate.execute("insert into good (name, description, price) values ('pizza', 'tasty pizza', 100);");
        jdbcTemplate.execute("insert into good (name, description, price) values ('apple', 'green apple', 15);");

        //when
        var response = given()
                .contentType(ContentType.JSON)
                .body(
                        new GetGoodsListRequest(
                                List.of(1L, 2L)
                        )
                )
                .when()
                .post("/goods/getGoodsList")
                .then()
                .statusCode(200)
                .extract()
                .as(GetGoodsListResponse.class);

        //then
        assertThat(response.goods().size()).isEqualTo(2);
        var good1 = response.goods().get(0);
        var good2 = response.goods().get(1);

        assertThat(good1.id()).isEqualTo(1);
        assertThat(good1.name()).isEqualTo("pizza");
        assertThat(good1.description()).isEqualTo("tasty pizza");
        assertThat(good1.price()).isEqualTo(new BigDecimal("100.00"));

        assertThat(good2.id()).isEqualTo(2);
        assertThat(good2.name()).isEqualTo("apple");
        assertThat(good2.description()).isEqualTo("green apple");
        assertThat(good2.price()).isEqualTo(new BigDecimal("15.00"));

    }
}
