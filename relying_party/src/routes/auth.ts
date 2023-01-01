import { Router, Request, Response, NextFunction } from "express";

import passport from "../libs/passport";

export const router = Router();

router.get("/login", (req: Request, res: Response, next: NextFunction) => {
    passport.authenticate("oidc")(req, res, next);
});

router.get("/callback", passport.authenticate("oidc", {
    successRedirect: "/",
    failureRedirect: "/login",
    session: true,
}));

router.get("/logout", (req: Request, res: Response, next: NextFunction) => {
  req.logout((err: any) => {if (err) { return next(err); }});
  res.redirect("/");
});
