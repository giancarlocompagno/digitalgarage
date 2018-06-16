package it.digitalgarage.marketplace.commons.fe.context.support;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.VfsResource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.util.Assert;

// TODO: Auto-generated Javadoc
/**
 * The Class WildcardReloadableResourceBundleMessageSource.
 */
public class WildcardReloadableResourceBundleMessageSource  extends ReloadableResourceBundleMessageSource{
	
	
	/** The resource pattern resolver. */
	private ResourcePatternResolver resourcePatternResolver = new PathMatchingResourcePatternResolver();

	   
	/* (non-Javadoc)
	 * @see org.springframework.context.support.ReloadableResourceBundleMessageSource#setBasenames(java.lang.String[])
	 */
	@Override
    public void setBasenames(String... basenames){
        if (basenames != null)
        {
            List<String> baseNames = new ArrayList<>();
            for (int i = 0; i < basenames.length; i++)
            {
                String basename = basenames[i];
                Assert.hasText(basename, "Basename must not be empty");
                try
                {
                    Resource[] resources = resourcePatternResolver.getResources(basename.trim());
                    for (int j = 0; j < resources.length; j++)
                    {
                        Resource resource = resources[j];
                        String uri = resource.getURI().toString();
                        String baseName = uri;
                        
                        // differ between file (contains the complete file path) and class path resources
                        if(resource instanceof VfsResource){
                        	baseName = "classpath:" + StringUtils.substringBetween(uri, ".jar", ".properties");
                        }
                        else if (resource instanceof FileSystemResource)
                        {
                            baseName = "classpath:" + StringUtils.substringBetween(uri, "/classes", ".properties");
                        }
                        else if (resource instanceof ClassPathResource) 
                        {
                            baseName = StringUtils.substringBefore(uri, ".properties");
                        }
                        
                        baseName = StringUtils.substringBefore(baseName, "_");
                        
                        baseNames.add(baseName);
                    }
                }
                catch (IOException e)
                {
                    logger.debug("No message source files found for basename " + basename + ".");
                }
            }
            Set<String> set = new LinkedHashSet<String>(baseNames);
            
            super.setBasenames(set.toArray(new String[set.size()]));
        }
    }

}
