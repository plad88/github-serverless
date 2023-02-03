import io
import json
import logging
import random
import mlflow

from fdk import response

tracking_uri = "https://onesait-telco-poc.onesaitplatform.com/controlpanel/modelsmanager"
model_uri = "onesait-platform://49832a9c2d7645caa0ef497706edef34@onesait-telco-poc.onesaitplatform.com/0/6657be3510d24fa9b8308e30d6b5fa7c/artifacts/model"
pyfunc_predictor = None

def initML():
    mlflow.set_tracking_uri(tracking_uri)

    pyfunc_predictor = mlflow.pyfunc.load_model(model_uri=model_uri)

    logging.getLogger().info("Predictor ready")

#initML()

def handler(ctx, data: io.BytesIO = None):
    try:
        answer = []
        json_obj = json.loads(data.getvalue())
        if isinstance(json_obj, list):
            answer = []
            logging.getLogger().info("input json:")
            #logging.getLogger().info(str(json_obj))
            for input in json_obj:
                answer.append(round(random.uniform(0, 1),2))
                #answer.append(pyfunc_predictor.predict([[ 7, 0.27, 0.36, 20.7, 0.045, 45, 170, 1.001, 3, 0.45, 8.8]])[0])
        else:
            answer = "input object is not an array of objects:" + str(json_obj)
            logging.getLogger().error('error isinstance(json_obj, list):' + isinstance(json_obj, list))
            raise Exception(answer)
    except (Exception, ValueError) as ex:

        logging.getLogger().error('error parsing json payload: ' + str(ex))

    logging.getLogger().info("Inside Python ML function")
    return response.Response(
        ctx, response_data=json.dumps(answer),
        headers={"Content-Type": "application/json"}
    )
