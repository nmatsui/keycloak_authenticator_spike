import { Router, Request, Response, NextFunction } from "express";

export const router = Router();

router.get("/", (req: Request, res: Response, next: NextFunction) => {
  if (req.isAuthenticated()) {
    res.render("authed", { user: req.user.userinfo });
  } else {
    res.render("index");
  }
});
