/*
 * Copyright 2021 - 2022 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.springframework.sbm;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.sbm.engine.recipe.Condition;
import org.springframework.sbm.engine.recipe.Recipe;
import org.springframework.sbm.spring.migration.actions.OpenRewriteRecipeAdapterAction;
import java.util.List;

@Configuration
public class SpringOpenRewriteRecipe {

    public static Recipe createRecipe(String name, org.openrewrite.Recipe recipe, Condition condition) {
        return Recipe.builder()
                .name(name)
                .description(recipe.getDescription())
                .condition(condition)
                .actions(List.of(new OpenRewriteRecipeAdapterAction(recipe)))
                .build();
    }


    @Bean
    Recipe recipeSpringBootRewriteTo27Migration(RewriteRecipesRepository repo) {
        org.openrewrite.Recipe r = repo.getRecipe("org.openrewrite.java.spring.boot2.UpgradeSpringBoot_2_7");

        return createRecipe("upgrade-boot-27", r, Condition.TRUE);
    }

    @Bean
    Recipe recipeSpringBootRewriteFrom27To30Migration(RewriteRecipesRepository repo) {
        org.openrewrite.Recipe r = repo.getRecipe("org.openrewrite.java.spring.boot3.UpgradeSpringBoot_3_0");

        return createRecipe("upgrade-boot-from-27-to-30", r, Condition.TRUE);
    }

}
