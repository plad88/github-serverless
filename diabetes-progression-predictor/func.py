import io
import json
import logging
import os
os.environ["HOME"] = "/tmp"
import random
import mlflow


from fdk import response

tracking_uri = "https://survivor.onesaitplatform.com/controlpanel/modelsmanager"
model_uri = "onesait-platform://588cca8132164ff1b62ba2f28273aeed@survivor.onesaitplatform.com/0/3079680c3ad84c02b0ac7631d9a59aae/artifacts/model"
global pyfunc_predictor

mlflow.set_tracking_uri(tracking_uri)
pyfunc_predictor = mlflow.pyfunc.load_model(model_uri=model_uri)
logging.getLogger().info("Diabetes Progression Predictor ready")

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
            values = []
            inputvector = []
            for input in json_obj:
                logging.getLogger().info("for")
                logging.getLogger().info("input: " + str(input))
                inputvector = [ input['age'], input['sex'], input['bmi'], input['bp'], input['s1'], input['s2'], input['s3'], input['s4'], input['s5'], input['s6']]
                values.append(inputvector)
                
            predict = pyfunc_predictor.predict(values)
            answer = predict.tolist()
            logging.getLogger().info("prediction")
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


