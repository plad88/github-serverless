import io
import json
import logging
import random
import mlflow

from fdk import response

tracking_uri = "https://onesait-telco-poc.onesaitplatform.com/controlpanel/modelsmanager"
model_uri = "onesait-platform://49832a9c2d7645caa0ef497706edef34@onesait-telco-poc.onesaitplatform.com/0/6657be3510d24fa9b8308e30d6b5fa7c/artifacts/model"
pyfunc_predictor = None

def handler(ctx, data: io.BytesIO = None):
    try:
        answer = []
        json_obj = json.loads(data.getvalue())
        if isinstance(json_obj, list):
            answer = []
            for input in json_obj:
                answer.append(round(random.uniform(0, 1),2))
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