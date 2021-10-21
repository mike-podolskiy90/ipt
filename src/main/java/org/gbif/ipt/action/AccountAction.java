/*
 * Copyright 2021 Global Biodiversity Information Facility (GBIF)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.gbif.ipt.action;

import org.gbif.ipt.config.AppConfig;
import org.gbif.ipt.model.User;
import org.gbif.ipt.service.admin.RegistrationManager;
import org.gbif.ipt.service.admin.UserAccountManager;
import org.gbif.ipt.struts2.SimpleTextProvider;
import org.gbif.ipt.validation.UserValidator;

import java.io.IOException;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.google.inject.Inject;

/**
 * Action handling account updates, such as changing username and password.
 */
public class AccountAction extends POSTAction {

  // logging
  private static final Logger LOG = LogManager.getLogger(AccountAction.class);

  private static final long serialVersionUID = 5092204508303815778L;

  private final UserAccountManager userManager;
  private final UserValidator validator = new UserValidator();

  private String email;
  private String newPassword;
  private String password2;
  private String currentPassword;
  private User user;

  @Inject
  public AccountAction(SimpleTextProvider textProvider, AppConfig cfg, RegistrationManager registrationManager,
    UserAccountManager userManager) {
    super(textProvider, cfg, registrationManager);
    this.userManager = userManager;
  }

  @Override
  public String execute() throws Exception {
    // check if any user is logged in right now - otherwise redirect to login page
    if (user == null) {
      return LOGIN;
    }
    return super.execute();
  }

  @Override
  public void prepare() {
    super.prepare();
    if (getCurrentUser() != null) {
      // modify existing user in session
      user = getCurrentUser();
    }
  }

  // TODO: 21/10/2021 do not save if email or password changed!
  @Override
  public String save() {
    try {
      LOG.debug(user);
      if (validator.validate(this, user)) {
        addActionMessage(getText("admin.user.account.updated"));
        LOG.debug("The user account has been updated");
        userManager.save();
        return SUCCESS;
      }
    } catch (IOException e) {
      addActionError(getText("admin.user.account.saveError"));
      LOG.error("The user account change could not be made: " + e.getMessage(), e);
      addActionError(e.getMessage());
    }
    return INPUT;
  }

  public String changePassword() {
    if (user != null) {
      String trimmedCurrentPassword = StringUtils.trimToNull(currentPassword);
      String trimmedNewPassword = StringUtils.trimToNull(newPassword);
      String trimmedNewPasswordConfirmation = StringUtils.trimToNull(password2);

      // all passwords must not be nulls
      if (trimmedCurrentPassword == null) {
        addFieldError("currentPassword", getText("validation.password.reentered"));
        LOG.error("The current password entered is empty");
      } else if (trimmedNewPassword == null) {
        addFieldError("newPassword", getText("validation.password.reentered"));
        LOG.error("The new password entered is empty");
      } else if (trimmedNewPasswordConfirmation == null) {
        addFieldError("password2", getText("validation.password.reentered"));
        LOG.error("The new password confirmation entered is empty");
      }
      // confirm current password is correct
      else if (!user.getPassword().equals(trimmedCurrentPassword)) {
        addFieldError("currentPassword", getText("validation.password.wrong"));
        LOG.error("The password does not match");
      }
      // passwords don't match?
      else if (!trimmedNewPassword.equals(trimmedNewPasswordConfirmation)) {
        addFieldError("password2", getText("validation.password2.wrong"));
        LOG.error("The passwords entered do not match");
        password2 = null;
        // otherwise, set password even if it's too short - it gets validated during save
      } else if (validator.validatePassword(this, newPassword)) {
        user.setPassword(newPassword);
        // erase data
        newPassword = null;
        currentPassword = null;
        password2 = null;
        addActionMessage(getText("admin.user.account.passwordChanged"));
        LOG.error("The password has been reset");
      }
    }

    return INPUT;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getPassword2() {
    return password2;
  }

  public void setPassword2(String password2) {
    this.password2 = password2;
  }

  public String getNewPassword() {
    return newPassword;
  }

  public void setNewPassword(String newPassword) {
    this.newPassword = newPassword;
  }

  public String getCurrentPassword() {
    return currentPassword;
  }

  public void setCurrentPassword(String currentPassword) {
    this.currentPassword = currentPassword;
  }

  public User getUser() {
    return user;
  }

  public void setUser(User user) {
    this.user = user;
  }
}
