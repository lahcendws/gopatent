// This file can be replaced during build by using the `fileReplacements` array.
// `ng build` replaces `environment.ts` with `environment.prod.ts`.
// The list of file replacements can be found in `angular.json`.

export const environment = {
  production: false,
  USER_API: 'http://localhost:8080/api/user',
  PATENT_SEARCH_API: "http://localhost:8080/api/patent/search/",
  FAVORITE_PATENT_API: "http://localhost:8080/api/favoritepatent",
  PATENT_API: "http://localhost:8080/api/patent",
  AUTH_API: 'http://localhost:8080/api/auth/',
  TOKEN_KEY: 'auth-token',
  USER_KEY: 'auth-user',

};

/*
 * For easier debugging in development mode, you can import the following file
 * to ignore zone related error stack frames such as `zone.run`, `zoneDelegate.invokeTask`.
 *
 * This import should be commented out in production mode because it will have a negative impact
 * on performance if an error is thrown.
 */
// import 'zone.js/plugins/zone-error';  // Included with Angular CLI.
