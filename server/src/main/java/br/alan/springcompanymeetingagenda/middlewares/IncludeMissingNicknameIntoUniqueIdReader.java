package br.alan.springcompanymeetingagenda.middlewares;

import com.google.common.base.Optional;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import io.swagger.annotations.ApiOperation;
import springfox.documentation.service.Operation;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.OperationBuilderPlugin;
import springfox.documentation.spi.service.contexts.OperationContext;
import springfox.documentation.swagger.common.SwaggerPluginSupport;

/**
 * IncludeMissingNicknameIntoUniqueIdReader
 * 
 * {@see <a href="https://stackoverflow.com/a/58955342">bdzzaid's answer</a>}
 */
@Order(SwaggerPluginSupport.SWAGGER_PLUGIN_ORDER + 2)
@Component
public class IncludeMissingNicknameIntoUniqueIdReader implements OperationBuilderPlugin {
    @Override
    public void apply(OperationContext context) {
        Optional<ApiOperation> methodAnnotation =
                context.findControllerAnnotation(ApiOperation.class);
        Operation operationBuilder = context.operationBuilder().build();
        String uniqueId =
                operationBuilder.getUniqueId().replaceAll("Using(GET|POST|PUT|DELETE)", "");
        if (methodAnnotation.isPresent()) {
            ApiOperation operation = methodAnnotation.get();
            if (operation.nickname() != null && !operation.nickname().isBlank()) {
                // Populate the value of nickname annotation into uniqueId
                context.operationBuilder().uniqueId(operation.nickname());
                context.operationBuilder().codegenMethodNameStem(operation.nickname());
            } else {
                context.operationBuilder().uniqueId(uniqueId);
                context.operationBuilder().codegenMethodNameStem(uniqueId);
            }
        } else {
            context.operationBuilder().uniqueId(uniqueId);
            context.operationBuilder().codegenMethodNameStem(uniqueId);
        }
    }

    @Override
    public boolean supports(DocumentationType delimiter) {
        return SwaggerPluginSupport.pluginDoesApply(delimiter);
    }
}
