import io
import json
import logging
import os
os.environ["HOME"] = "/tmp"
import random
import mlflow


from fdk import response

tracking_uri = "https://onesait-telco-poc.onesaitplatform.com/controlpanel/modelsmanager"
model_uri = "onesait-platform://49832a9c2d7645caa0ef497706edef34@onesait-telco-poc.onesaitplatform.com/0/19286433c9d6483e8a027ebb76aa11ce/artifacts/model"
global pyfunc_predictor

mlflow.set_tracking_uri(tracking_uri)
pyfunc_predictor = mlflow.pyfunc.load_model(model_uri=model_uri)
logging.getLogger().info("Predictor ready")

def handler(ctx, data: io.BytesIO = None):
    try:
        logging.getLogger().info("Try")
        answer = []
        json_obj = json.loads(data.getvalue())
        logging.getLogger().info("json_obj")
        logging.getLogger().info(str(json_obj))
        if isinstance(json_obj, list):
            logging.getLogger().info("isinstance")
            answer = []
            for input in json_obj:
                logging.getLogger().info("for")
                logging.getLogger().info("input: " + str(input))
                #answer.append(round(random.uniform(0, 1),2))
                predict = pyfunc_predictor.predict([[ 7, 0.27, 0.36, 20.7, 0.045, 45, 170, 1.001, 3, 0.45, 8.8]])
                predict = pyfunc_predictor.predict([[ 6.9, 0.36, 0.34, 4.2, 0.018, 57, 119 ,0.9898, 3.28 , 0.36, 12.7]])
                
                logging.getLogger().info("prediction")
                logging.getLogger().info(predict)
                answer.append(predict[0])
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