import { Router, Request, Response, NextFunction } from "express";

import { isLoggedIn } from "../libs/utils";

export const router = Router();

router.get("/tokeninfo", isLoggedIn, (req: Request, res: Response, next: NextFunction) => {
  res.render("tokeninfo", {
    id_token: JSON.stringify(req.user?.token.id_token, null, 2),
    access_token: JSON.stringify(req.user?.token.access_token, null, 2),
    refresh_token: JSON.stringify(req.user?.token.refresh_token, null, 2),
  })
});
