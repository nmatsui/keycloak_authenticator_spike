import express, { Request, Response, NextFunction } from "express";
import logger from "morgan";

import { router as usersRouter } from "./routes/users";

const app = express();

app.use(logger("dev"));
app.use(express.json());

app.use("/users", usersRouter);

app.use((req: Request, res: Response) => {
    res.status(404);
    res.json({
        status: 404,
        message: 'not found'
    });
});
app.use((err: any, req: Request, res: Response, next: NextFunction) => {
    res.locals.message = err.message;
    res.locals.error = req.app.get("env") === "development" ? err : {};
    res.status(err.status || 500);
    const msg = {
        status: res.statusCode,
        message: err.message
    }
    console.error(JSON.stringify(msg));
    res.json(msg);
});

app.listen(3000,()=>{
    console.log('start port to 3000')
});

module.exports = app;
