import { Injectable } from '@angular/core';
import { AuthConfig, OAuthService } from 'angular-oauth2-oidc'

@Injectable({
  providedIn: 'root'
})
export class AuthGoogleService {

  constructor(private oauthService: OAuthService) {
    this.initLogin();
  }

  initLogin() {
    const config: AuthConfig = {
      issuer: 'https://accounts.google.com',
      strictDiscoveryDocumentValidation: false,
      clientId: '872531465200-phv9i3mh1h7vu01dtq1erpvld7s06vrd.apps.googleusercontent.com',
      redirectUri: window.location.origin + '/login-success',
      scope: 'openid profile email',
    }

    this.oauthService.configure(config);
    this.oauthService.setupAutomaticSilentRefresh();
    this.oauthService.loadDiscoveryDocumentAndTryLogin().then(r => {});
  }

  login() {
    this.oauthService.initImplicitFlow();
    // const data = JSON.stringify(this.oauthService.getIdentityClaims());
    // console.log(this.oauthService.getIdentityClaims());
  }

  logout() {
    this.oauthService.logOut();
  }

  getProfile() {
    return this.oauthService.getIdentityClaims();
  }
  getToken() {
    return this.oauthService.getAccessToken();
  }
  getIdToken() {
    return this.oauthService.getIdToken();
  }
  revokeTokenAndLogout(){
    this.oauthService.revokeTokenAndLogout();
  }

}
