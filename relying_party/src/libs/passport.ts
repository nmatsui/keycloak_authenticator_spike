import dotenv from "dotenv";
import passport from "passport";
import { Issuer, Strategy, TokenSet, UserinfoResponse } from "openid-client";

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
      return done(null, userinfo);
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
