#!groovy
// import hudson.security.*
// import jenkins.model.*
// import hudson.security.csrf.DefaultCrumbIssuer
// def instance = Jenkins.getInstance()
// def hudsonRealm = new HudsonPrivateSecurityRealm(false)
// def users = hudsonRealm.getAllUsers()
// instance.setCrumbIssuer(new DefaultCrumbIssuer(true))
// instance.save()
// users_s = users.collect { it.toString() } 
//
// // Create the admin user account if it doesn't already exist.
// if ("{{ jenkins_admin_username }}" in users_s) {
//     println "Admin user already exists - updating password"
//
//     def user = hudson.model.User.get('{{ jenkins_admin_username }}');
//     def password = hudson.security.HudsonPrivateSecurityRealm.Details.fromPlainPassword('{{ jenkins_admin_password }}')
//     user.addProperty(password)
//     user.save()
// }
// else {
//     println "--> creating local admin user"
//
//     hudsonRealm.createAccount('{{ jenkins_admin_username }}', '{{ jenkins_admin_password }}')
//     instance.setSecurityRealm(hudsonRealm)
//
//     def strategy = new FullControlOnceLoggedInAuthorizationStrategy()
//     instance.setAuthorizationStrategy(strategy)
//    

import jenkins.model.*
import hudson.security.*
import jenkins.install.installState

def instance = Jenkins.getInstance()

println "--> creating local user 'admin'"

def hudsonRealm = new HudsonPrivateSecurityRealm(false)
// hudsonRealm.createAccount('{{ jenkins_admin_username }}','{{ jenkins_admin_password }}')
hudsonRealm.createAccount('admin','1234')
instance.setSecurityRealm(hudsonRealm)

def strategy = new FullControlOnceLoggedInAuthorizationStrategy()
strategy.setAllowAnonymousRead(false)
instance.setAuthorizationStrategy(strategy)

if (!instance.installState.isSetupComplete()) {
  println '--> Neutering SetupWizard'
  InstallState.INITIAL_SETUP_COMPLETED.initializeState()
}

instance.save()

