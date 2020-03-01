package br.alan.springcompanymeetingagenda.middlewares;

import com.google.common.base.Optional;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.Authorization;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.OperationBuilderPlugin;
import springfox.documentation.spi.service.contexts.OperationContext;
import springfox.documentation.spring.web.DescriptionResolver;
import springfox.documentation.swagger.common.SwaggerPluginSupport;

/**
 * OperationNotesResourcesReader
 * 
 * Provides extra notes for request methods annotated with
 * {@link io.swagger.annotations.Authorization}.
 */
@RequiredArgsConstructor
@Order(SwaggerPluginSupport.SWAGGER_PLUGIN_ORDER + 1)
@Slf4j
@Component
public class OperationNotesResourcesReader implements OperationBuilderPlugin {

    private final DescriptionResolver descriptions;

    @Override
    public void apply(OperationContext context) {
        try {
            Optional<ApiOperation> annotation = context.findAnnotation(ApiOperation.class);
            String notes = (annotation.isPresent() && StringUtils.hasText(annotation.get().notes()))
                    ? (annotation.get().notes())
                    : "";
            String apiRoleAccessNoteText = "";
            Optional<Authorization> authorizeAnnotation =
                    context.findAnnotation(Authorization.class);
            if (authorizeAnnotation.isPresent()) {
                apiRoleAccessNoteText =
                        "<b>Access Privileges & Rules : </b>" + authorizeAnnotation.get().value();
            }
            notes = apiRoleAccessNoteText + " \n\n " + notes;
            // add the note text to the Swagger UI
            context.operationBuilder().notes(descriptions.resolve(notes));
        } catch (Exception e) {
            log.error("Error when creating Swagger documentation for security Roles: " + e);
        }
    }

    @Override
    public boolean supports(DocumentationType delimiter) {
        return SwaggerPluginSupport.pluginDoesApply(delimiter);
    }
}
