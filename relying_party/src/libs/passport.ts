import dotenv from "dotenv";
import passport from "passport";
import jwt_decode from "jwt-decode";
import { Issuer, Strategy, TokenSet, UserinfoResponse } from "openid-client";

declare global {
  namespace Express {
    interface User {
      userinfo: UserinfoResponse,
      token: {
        id_token: any,
        access_token: any,
        refresh_token: any,
      }
    }
  }
}

dotenv.config();

(async function () {
  const issuer = await Issuer.discover(process.env?.ISSUER_BASE_URL || "http://localhost:8080/");
  const client = new issuer.Client({
    client_id: process.env?.CLIENT_ID || "client",
    client_secret: process.env?.CLIENT_SECRET || "your_client_secret",
    redirect_uris: [process.env?.REDIRECT_URL || "http://localhost:3000/callback"],
    response_types: ["code"]
  });

  passport.use(
    "oidc",
    new Strategy({client}, (tokenSet: TokenSet, userinfo: UserinfoResponse, done: any) => {
      const user = {
        userinfo: userinfo,
        token: {
          id_token: tokenSet?.id_token ? jwt_decode(tokenSet.id_token) : undefined,
          access_token: tokenSet?.access_token ? jwt_decode(tokenSet.access_token) : undefined,
          refresh_token: tokenSet?.refresh_token ? jwt_decode(tokenSet.refresh_token) : undefined
        }
      }
      return done(null, user);
    })
  );

  passport.serializeUser((user: any, done: any) => {
    done(null, user);
  });

  passport.deserializeUser((user: any, done: any) => {
    done(null, user);
  });
})();

export default passport;
