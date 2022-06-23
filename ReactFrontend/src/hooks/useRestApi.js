import { useState, useEffect, setState} from 'react';

const useRestApi = (key, initialValue) => {
  const [value, setValue] = useState(() => {
      fetch(`http:\/\/localhost:4000/appData/${key}`)
          .then(response => response.json())
          .then(data => setValue(data.values));
      return initialValue;
  });

  useEffect(() => {
    const objectToSave = {
        id : key,
        values : value
    };
    const messageBody = JSON.stringify(objectToSave);
    fetch(`http:\/\/localhost:4000/appData/${key}`, {
       method: 'PUT',
       headers: {
         'Content-Type': 'application/json'
       },
       body: messageBody
    });

  }, [key, value]);

  return [value, setValue];
};

export default useRestApi;
