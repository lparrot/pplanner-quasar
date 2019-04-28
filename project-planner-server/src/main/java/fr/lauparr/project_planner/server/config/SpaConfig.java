package fr.lauparr.project_planner.server.config;

import io.micrometer.core.instrument.util.IOUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.web.ResourceProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.resource.PathResourceResolver;
import org.springframework.web.servlet.resource.TransformedResource;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

@Configuration
public class SpaConfig implements WebMvcConfigurer {

  private static final String API_PATH = "/api";
  private static final String PATH_PATTERNS = "/**";
  private static final String FRONT_CONTROLLER = "index.html";
  private static final String CONTEXT_PATH_PLACEHOLDER = "#context-path#";
  private static final Charset FRONT_CONTROLLER_ENCODING = StandardCharsets.UTF_8;

  private final String contextPath;
  private final ResourceProperties resourceProperties;

  public SpaConfig(@Value("${server.servlet.context-path}") final String contextPath, final ResourceProperties resourceProperties) {
    this.contextPath = contextPath;
    this.resourceProperties = resourceProperties;
  }

  @Override
  public void addViewControllers(ViewControllerRegistry pRegistry) {
    pRegistry.addViewController("/").setViewName("forward:/" + FRONT_CONTROLLER);
  }

  @Override
  public void addResourceHandlers(final ResourceHandlerRegistry registry) {
    registry.addResourceHandler(PATH_PATTERNS)
            .addResourceLocations(this.resourceProperties.getStaticLocations())
            .resourceChain(true)
            .addResolver(new SinglePageAppResourceResolver());
  }

  private class SinglePageAppResourceResolver extends PathResourceResolver {

    public static final String URL_SEPARATOR = "/";

    private TransformedResource transformedResource(final Resource resource) throws IOException {
      String fileContent = IOUtils.toString(resource.getInputStream(), FRONT_CONTROLLER_ENCODING);
      fileContent = fileContent.replace(CONTEXT_PATH_PLACEHOLDER, SpaConfig.this.contextPath + URL_SEPARATOR);
      return new TransformedResource(resource, fileContent.getBytes());
    }

    @Override
    protected Resource getResource(final String resourcePath, final Resource location) throws IOException {
      Resource resource = location.createRelative(resourcePath);
      if (resource.exists() && resource.isReadable()) {
        // if the asked resource is index.html, we serve it with the base-href rewritten
        if (resourcePath.contains(FRONT_CONTROLLER)) {
          return transformedResource(resource);
        }

        return resource;
      }

      // do not serve a Resource on an reserved URI
      if ((URL_SEPARATOR + resourcePath).startsWith(API_PATH)) {
        return null;
      }

      // we have just refreshed a page, no ?
      resource = location.createRelative(FRONT_CONTROLLER);
      if (resource.exists() && resource.isReadable()) {
        return transformedResource(resource);
      }

      return null;
    }
  }

}