package api.git.core.service.gitApiDemoTesting;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * Created by andreapolcz on 6/15/17.
 */
@Component
public class Configuration {

    @Value("${git.username}")
    private String gitUserName;
    @Value("${git.passw0rd}")
    private String gitPassw0rd;
    @Value("${git.token}")
    private String gitToken;


    public String getGitUserName() {
        return gitUserName;
    }

    public void setGitUserName(String gitUserName) {
        this.gitUserName = gitUserName;
    }

    public String getGitPassw0rd() {
        return gitPassw0rd;
    }

    public void setGitPassw0rd(String gitPassw0rd) {
        this.gitPassw0rd = gitPassw0rd;
    }

    public String getGitToken() {
        return gitToken;
    }

    public void setGitToken(String gitToken) {
        this.gitToken = gitToken;
    }

}
