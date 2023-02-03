import org.scalatra.LifeCycle
import javax.servlet.ServletContext
import com.gaguena.sorders.rest.controller.OrderRest
import com.gaguena.sorders.repository._
import com.gaguena.sorders.rest.controller.ProductRest
import com.gaguena.sorders.service.RepositoryComponent
import com.gaguena.sorders.repository.ItemRepository
import com.gaguena.sorders.repository.ProductRepository

class ScalatraBootstrap extends LifeCycle with RepositoryComponent {

  override def init(context: ServletContext) {
    context.mount(new OrderRest, "/orders/*")
    context.mount(new ProductRest, "/products/*")

    init()
  }

  def init() = {
    println("Criando database scahema")
    orderRepository.createSchema()
    itemRepository.createSchema()
    productRepository.createSchema()
  }
}
